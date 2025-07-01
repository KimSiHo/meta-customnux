#!/bin/sh

if [ -f /tmp/swu.status ]; then
    echo "[swu-watcher] SWUpdate 성공 확인됨. 재부팅 시작."
    rm /tmp/swu.status
    echo 'hi'
    sleep 10
    reboot
else
    echo "[swu-watcher] 상태 파일 없음. 작업 안함."
fi
