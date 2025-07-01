#!/bin/sh
while ! ip link show wlan0 > /dev/null 2>&1; do
    echo "[wlan-setup] Waiting for wlan0 interface..."
    sleep 0.5
done

rfkill unblock all
sleep 1
wpa_supplicant -B -i wlan0 -c /etc/wpa_supplicant.conf
sleep 2
udhcpc -i wlan0
sleep 2
dm