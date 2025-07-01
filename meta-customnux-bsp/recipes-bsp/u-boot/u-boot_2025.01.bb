require u-boot-common.inc
require u-boot.inc

inherit externalsrc
EXTERNALSRC = "/home/sihokim/projects/u-boot"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI += " \
    file://u-boot-initial-env \
    file://extlinux_a.conf \
    file://extlinux_b.conf \
"

DEPENDS += "bc-native dtc-native python3-setuptools-native u-boot-tools-native"

UBOOT_MACHINE = "rpi_4_defconfig"

do_compile:append() {
    mkenvimage -s 0x4000 -o ${WORKDIR}/uboot.env ${WORKDIR}/u-boot-initial-env
}

do_install:append() {
    install -d ${D}/boot
    install -m 0644 ${WORKDIR}/uboot.env ${D}/boot/

    install -d ${D}/boot/extlinux
    install -m 0644 ${WORKDIR}/extlinux_a.conf ${D}/boot/extlinux/extlinux_a.conf
    install -m 0644 ${WORKDIR}/extlinux_b.conf ${D}/boot/extlinux/extlinux_b.conf
}

do_deploy:append() {
    install -m 0644 ${WORKDIR}/uboot.env ${DEPLOYDIR}/uboot.env
    install -m 0644 ${WORKDIR}/extlinux_a.conf ${DEPLOYDIR}/extlinux_a.conf
    install -m 0644 ${WORKDIR}/extlinux_b.conf ${DEPLOYDIR}/extlinux_b.conf
}
