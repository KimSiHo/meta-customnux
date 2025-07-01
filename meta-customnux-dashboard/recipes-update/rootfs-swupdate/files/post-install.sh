#!/bin/sh

boot_part=$(fw_printenv boot_part | cut -d '=' -f2)

if [ "$boot_part" = "2" ]; then
    if cp /boot/extlinux/extlinux_b.conf /boot/extlinux/extlinux.conf 2>/dev/null; then
        fw_setenv boot_part 3
        echo "[ota-switch] Switched to boot_part 3"
    else
        echo "[ota-switch] Failed to switch to boot_part 3"
    fi
elif [ "$boot_part" = "3" ]; then
    if cp /boot/extlinux/extlinux_a.conf /boot/extlinux/extlinux.conf 2>/dev/null; then
        fw_setenv boot_part 2
        echo "[ota-switch] Switched to boot_part 2"
    else
        echo "[ota-switch] Failed to switch to boot_part 2"
    fi
else
    echo "[ota-switch] Unknown boot_part: '$boot_part'"
fi

echo "UPDATE_SUCCESS" > /tmp/swu.status
exit 0
