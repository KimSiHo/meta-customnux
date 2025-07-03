BOOTFILES_DIR_NAME = "."

# FIXME PARTUUID 적용하기 (안되는데 무슨 이유인지 파악 x)
CMDLINE_ROOTFS = "root=/dev/mmcblk0p2 ${CMDLINE_ROOT_FSTYPE} rootwait rootdelay=50"
CMDLINE_SERIAL = "console=ttyAMA0,115200 console=tty1"
CMDLINE_KGDB = "kgdboc=ttyAMA0,115200 kgdbwait"

# console=ttyAMA1,115200