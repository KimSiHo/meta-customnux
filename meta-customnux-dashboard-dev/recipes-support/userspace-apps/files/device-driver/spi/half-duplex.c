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
#define SPEED 1000000
#define TX_LEN 2
#define RX_LEN 3

int main() {
    int fd = open(DEVICE, O_RDWR);
    if (fd < 0) {
        perror("open");
        return EXIT_FAILURE;
    }

    uint8_t mode = SPI_MODE;
    uint8_t bits = BITS_PER_WORD;
    uint32_t speed = SPEED;

    ioctl(fd, SPI_IOC_WR_MODE, &mode);
    ioctl(fd, SPI_IOC_WR_BITS_PER_WORD, &bits);
    ioctl(fd, SPI_IOC_WR_MAX_SPEED_HZ, &speed);

    // Step 1: write register address (TX)
    uint8_t tx_buf[TX_LEN] = { 0x10, 0x00 }; // 예: 주소 + dummy
    struct spi_ioc_transfer tx = {
        .tx_buf = (unsigned long)tx_buf,
        .rx_buf = 0,
        .len = TX_LEN,
        .speed_hz = speed,
        .bits_per_word = bits,
        .cs_change = 0, // CS 유지
    };

    // Step 2: read data from register (RX)
    uint8_t rx_buf[RX_LEN] = { 0 };
    struct spi_ioc_transfer rx = {
        .tx_buf = 0,
        .rx_buf = (unsigned long)rx_buf,
        .len = RX_LEN,
        .speed_hz = speed,
        .bits_per_word = bits,
        .cs_change = 0, // CS 유지
    };

    struct spi_ioc_transfer tr[] = { tx, rx };
    int ret = ioctl(fd, SPI_IOC_MESSAGE(2), tr);
    if (ret < 1) {
        perror("SPI_IOC_MESSAGE");
        close(fd);
        return EXIT_FAILURE;
    }

    printf("Read: ");
    for (int i = 0; i < RX_LEN; i++)
        printf("0x%02X ", rx_buf[i]);
    printf("\n");

    close(fd);
    return EXIT_SUCCESS;
}
