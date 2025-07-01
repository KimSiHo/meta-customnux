#!/bin/bash

RESULT_FILE="results.txt"
SYSFS_TRIGGER="/sys/class/spi_master/spi0/dma_benchmark_trigger"
SYSFS_RESULT="/sys/class/spi_master/spi0/dma_benchmark_result"

# 결과 파일 초기화
echo "SPI DMA Benchmark Results" > "$RESULT_FILE"
echo "Length    Result" >> "$RESULT_FILE"

for len in 8 16 32 64 96 128 160 192 256; do
    echo $len > "$SYSFS_TRIGGER"
    sleep 10
    result=$(cat "$SYSFS_RESULT" | tr -d '\n')
    echo "$len    $result" >> "$RESULT_FILE"
done
