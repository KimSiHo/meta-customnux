#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <linux/spi/spidev.h>

#define DEVICE "/dev/spidev0.1"
#define SPI_MODE SPI_MODE_0
#define BITS_PER_WORD 8
#define SPEED 1000000  // 1MHz
#define TRANSFER_SIZE 4

int main() {
    int fd = open(DEVICE, O_RDWR);
    if (fd < 0) {
        perror("open");
        return EXIT_FAILURE;
    }

    // SPI 모드 설정
    uint8_t mode = SPI_MODE;
    ioctl(fd, SPI_IOC_WR_MODE, &mode);

    // 비트 수 설정
    uint8_t bits = BITS_PER_WORD;
    ioctl(fd, SPI_IOC_WR_BITS_PER_WORD, &bits);

    // 속도 설정
    uint32_t speed = SPEED;
    ioctl(fd, SPI_IOC_WR_MAX_SPEED_HZ, &speed);

    // 송신/수신 버퍼
    uint8_t tx_buf[TRANSFER_SIZE] = { 0xAA, 0xBB, 0xCC, 0xDD };
    uint8_t rx_buf[TRANSFER_SIZE] = { 0 };

    struct spi_ioc_transfer tr = {
        .tx_buf = (unsigned long)tx_buf,
        .rx_buf = (unsigned long)rx_buf,
        .len = TRANSFER_SIZE,
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

    // 결과 출력
    printf("Sent:     ");
    for (int i = 0; i < TRANSFER_SIZE; i++)
        printf("0x%02X ", tx_buf[i]);
    printf("\nReceived: ");
    for (int i = 0; i < TRANSFER_SIZE; i++)
        printf("0x%02X ", rx_buf[i]);
    printf("\n");

    close(fd);
    return EXIT_SUCCESS;
}
