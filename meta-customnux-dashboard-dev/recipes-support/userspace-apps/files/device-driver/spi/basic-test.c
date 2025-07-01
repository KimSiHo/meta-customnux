#include <stdio.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdint.h>
#include <sys/ioctl.h>
#include <linux/spi/spidev.h>

#define DEVICE "/dev/spidev0.1"
#define SPI_MODE SPI_MODE_0
#define BITS_PER_WORD 8
#define SPEED 1000000 // 1MHz

int main() {
    int fd = open(DEVICE, O_RDWR);
    if (fd < 0) {
        perror("open");
        return EXIT_FAILURE;
    }

    // SPI 모드 설정 (MODE 0)
    uint8_t mode = SPI_MODE;
    if (ioctl(fd, SPI_IOC_WR_MODE, &mode) == -1) {
        perror("SPI_IOC_WR_MODE");
        close(fd);
        return EXIT_FAILURE;
    }

    // 비트 수 설정
    uint8_t bits = BITS_PER_WORD;
    if (ioctl(fd, SPI_IOC_WR_BITS_PER_WORD, &bits) == -1) {
        perror("SPI_IOC_WR_BITS_PER_WORD");
        close(fd);
        return EXIT_FAILURE;
    }

    // 속도 설정
    uint32_t speed = SPEED;
    if (ioctl(fd, SPI_IOC_WR_MAX_SPEED_HZ, &speed) == -1) {
        perror("SPI_IOC_WR_MAX_SPEED_HZ");
        close(fd);
        return EXIT_FAILURE;
    }

    // 송신/수신 버퍼 준비
    uint8_t tx_buf[1] = { 0x9A };  // 전송할 바이트
    uint8_t rx_buf[1] = { 0 };

    struct spi_ioc_transfer tr = {
        .tx_buf = (unsigned long)tx_buf,
        .rx_buf = (unsigned long)rx_buf,
        .len = sizeof(tx_buf),
        .delay_usecs = 0,
        .speed_hz = speed,
        .bits_per_word = bits,
    };

    // SPI 메시지 전송/수신
    int ret = ioctl(fd, SPI_IOC_MESSAGE(1), &tr);
    if (ret < 1) {
        perror("SPI_IOC_MESSAGE");
        close(fd);
        return EXIT_FAILURE;
    }

    printf("Sent: 0x%02X, Received: 0x%02X\n", tx_buf[0], rx_buf[0]);

    close(fd);
    return EXIT_SUCCESS;
}
