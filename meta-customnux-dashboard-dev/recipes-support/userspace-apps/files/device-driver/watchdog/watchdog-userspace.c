#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <signal.h>
#include <linux/watchdog.h>
#include <sys/ioctl.h>

static int fd;

void cleanup(int sig) {
    printf("\nCaught signal %d, disabling watchdog...\n", sig);
    write(fd, "V", 1);
    close(fd);
    exit(0);
}

int main(void)
{
    signal(SIGINT, cleanup);
    signal(SIGTERM, cleanup);

    int timeout;
    struct watchdog_info info;
    FILE *pidfile;

    pid_t pid = getpid();
    pidfile = fopen("/var/run/watchdog.pid", "w");
    if (pidfile) {
        fprintf(pidfile, "%d\n", pid);
        fclose(pidfile);
    } else {
        perror("fopen /var/run/watchdog.pid");
    }

    fd = open("/dev/watchdog", O_RDWR);
    if (fd < 0) {
        perror("open");
        return 1;
    }

    // 1. 디바이스 정보 조회
    if (ioctl(fd, WDIOC_GETSUPPORT, &info) == 0) {
        printf("Watchdog identity: %s\n", info.identity);
        printf("Firmware version: %u\n", info.firmware_version);
        printf("Options: 0x%x\n", info.options);
    } else {
        perror("WDIOC_GETSUPPORT");
    }

    // 2. 현재 타임아웃 조회
    if (ioctl(fd, WDIOC_GETTIMEOUT, &timeout) == 0) {
        printf("Current timeout: %d seconds\n", timeout);
    } else {
        perror("WDIOC_GETTIMEOUT");
    }

    // 3. 새로운 타임아웃 설정
    timeout = 15;
    if (ioctl(fd, WDIOC_SETTIMEOUT, &timeout) == 0) {
        printf("New timeout set to: %d seconds\n", timeout);
    } else {
        perror("WDIOC_SETTIMEOUT");
    }

    printf("Starting watchdog keepalive loop.\n");

    // 4. 주기적 ping (KEEPALIVE)
    while (1) {
        if (ioctl(fd, WDIOC_KEEPALIVE, 0) < 0) {
            perror("WDIOC_KEEPALIVE");
            break;
        }
        sleep(timeout / 2);  // 타임아웃의 절반 주기로 ping
    }

    write(fd, "V", 1);
    close(fd);

    return 0;
}
