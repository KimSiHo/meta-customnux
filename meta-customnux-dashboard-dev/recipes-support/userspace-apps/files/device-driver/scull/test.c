#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <errno.h>

#include "scull/scull_user.h"

#define DEVICE_PATH "/dev/scull0"
#define TEST_DATA "Hello, SCULL!"

int main() {
    int fd;
    ssize_t ret;
    char read_buf[128] = {0};

    // 1. open file
    fd = open(DEVICE_PATH, O_RDWR);
    if (fd < 0) {
        perror("Failed to open device");
        return EXIT_FAILURE;
    }
    printf("Opened device: %s\n", DEVICE_PATH);

    // 2. write do device file
    printf("Writing to the device: '%s'\n", TEST_DATA);
    ret = write(fd, TEST_DATA, strlen(TEST_DATA));
    if (ret < 0) {
        perror("Failed to write to the device");
        close(fd);
        return EXIT_FAILURE;
    }
    printf("Written '%ld' bytes to the device\n", ret);

    // 3. file seek
    printf("Seeking to the beginning of the file\n");
    if (lseek(fd, 0, SEEK_SET) < 0) {
        perror("Failed to seek");
        close(fd);
        return EXIT_FAILURE;
    }

    // 4. read
    printf("Reading from the device\n");
    ret = read(fd, read_buf, sizeof(read_buf) - 1);
    if (ret < 0) {
        perror("Failed to read from the device");
        close(fd);
        return EXIT_FAILURE;
    }
    read_buf[ret] = '\0';
    printf("Read '%ld' bytes: '%s'\n", ret, read_buf);

    // 5. call ioctl
    printf("call IOCTL\n");
    int quantum = 0;
    if (ioctl(fd, SCULL_IOCGQUANTUM, &quantum) < 0) {
        perror("Failed to perform IOCTL");
        close(fd);
        return EXIT_FAILURE;
    }
    printf("SCULL quantum: %d\n", quantum);

    // 6. close file
    close(fd);
    printf("Device closed successfully\n");

    return EXIT_SUCCESS;
}
