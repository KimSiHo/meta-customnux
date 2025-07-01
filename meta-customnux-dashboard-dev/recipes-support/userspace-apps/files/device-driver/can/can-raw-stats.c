#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <linux/can.h>
#include <linux/can/raw.h>
#include <linux/can/error.h>
#include <errno.h>
#include <net/if.h>
#include <sys/ioctl.h>
#include <sys/time.h>

int can_socket;

void *send_error_thread(void *arg) {
    struct can_frame err_frame;
    err_frame.can_id = CAN_ERR_FLAG | CAN_ERR_ACK;
    err_frame.len = 0;  // 에러 상세 정보 없음
    printf("DEBUG: can_id = 0x%08X\n", err_frame.can_id);

    while (1) {
        if (write(can_socket, &err_frame, sizeof(err_frame)) != sizeof(err_frame)) {
            perror("[ERR TX] write");
        } else {
            printf("[ERR TX] sent CAN error frame (CAN_ERR_ACK)\n");
        }
        usleep(10000000);  // 10초마다 전송
    }
    return NULL;
}

void *send_thread(void *arg) {
    struct can_frame frame;
    frame.can_id = CAN_ID_TEST_MSG_1 & CAN_SFF_MASK;
    frame.len = 1;
    frame.data[0] = 0x01;

    while (1) {
        if (write(can_socket, &frame, sizeof(frame)) != sizeof(frame)) {
            perror("write");
        } else {
            printf("[TX] sent CAN frame\n");
        }
        usleep(5000000);  // 5초 대기
    }
    return NULL;
}

void *recv_thread(void *arg) {
    struct can_frame frame;
    fd_set readfds;
    struct timeval timeout;

    while (1) {
        FD_ZERO(&readfds);
        FD_SET(can_socket, &readfds);
        timeout.tv_sec = 8;
        timeout.tv_usec = 0;

        int ret = select(can_socket + 1, &readfds, NULL, NULL, &timeout);
        if (ret < 0) {
            perror("select");
            break;
        } else if (ret == 0) {
            printf("[RX] timeout\n");
            continue;
        }

        if (FD_ISSET(can_socket, &readfds)) {
            ssize_t nbytes = read(can_socket, &frame, sizeof(frame));
            if (nbytes < 0) {
                perror("read");
                break;
            }

            printf("[RX] CAN ID: 0x%08X DLC: %d DATA:", frame.can_id, frame.can_dlc);
            for (int i = 0; i < frame.can_dlc; i++) {
                printf(" %02X", frame.data[i]);
            }
            printf("\n");
        }
    }
    return NULL;
}

void *stats_thread(void *arg) {
    struct can_raw_stats stats;
    socklen_t optlen = sizeof(stats);

    while (1) {
        if (getsockopt(can_socket, SOL_CAN_RAW, CAN_RAW_STATS, &stats, &optlen) < 0) {
            perror("getsockopt(CAN_RAW_STATS)");
        } else {
            printf("\n=== CAN Raw Socket Stats ===\n");
            printf("stats addr            : %p\n", (void *)&stats);
            printf("RX Frames   : %u\n", stats.rx_frame_count);
            printf("TX Frames   : %u\n", stats.tx_frame_count);
            printf("ERR Frames  : %u\n", stats.err_frame_count);
        }

        sleep(20);  // 20초 주기로 갱신
    }

    return NULL;
}

int main() {
    setvbuf(stdout, NULL, _IONBF, 0);  // stdout 버퍼링 비활성화

    struct sockaddr_can addr;
    struct ifreq ifr;
    const char *ifname = "can0";

    can_socket = socket(PF_CAN, SOCK_RAW, CAN_RAW);
    if (can_socket < 0) {
        perror("socket");
        return 1;
    }

    // 루프백 수신 허용
    int recv_own = 1;
    setsockopt(can_socket, SOL_CAN_RAW, CAN_RAW_RECV_OWN_MSGS, &recv_own, sizeof(recv_own));

    // ACK 에러와 BUSOFF 에러 필터링 요청
    can_err_mask_t err_mask = CAN_ERR_ACK | CAN_ERR_BUSOFF;
    setsockopt(can_socket, SOL_CAN_RAW, CAN_RAW_ERR_FILTER, &err_mask, sizeof(err_mask));

    // GROUP_TEST 필터링
    struct can_filter filter;
    filter.can_id   = CAN_GROUP_TEST;
    filter.can_mask = CAN_ID_GROUP_MASK;
    setsockopt(can_socket, SOL_CAN_RAW, CAN_RAW_FILTER, &filter, sizeof(filter));

    // 인터페이스 설정 및 바인딩
    strncpy(ifr.ifr_name, ifname, IFNAMSIZ - 1);
    if (ioctl(can_socket, SIOCGIFINDEX, &ifr) < 0) {
        perror("ioctl");
        return 1;
    }

    addr.can_family = AF_CAN;
    addr.can_ifindex = ifr.ifr_ifindex;

    if (bind(can_socket, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
        perror("bind");
        return 1;
    }

    pthread_t tx_tid, rx_tid, stats_tid, err_tid;

    pthread_create(&tx_tid, NULL, send_thread, NULL);
    pthread_create(&rx_tid, NULL, recv_thread, NULL);
    pthread_create(&stats_tid, NULL, stats_thread, NULL);
    pthread_create(&err_tid, NULL, send_error_thread, NULL);

    pthread_join(tx_tid, NULL);
    pthread_join(rx_tid, NULL);
    pthread_join(stats_tid, NULL);
    pthread_join(err_tid, NULL);

    close(can_socket);
    return 0;
}
