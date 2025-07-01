SUMMARY = "Kernel UAPI headers for development (from Linux 6.11)"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit kernel-arch

EXTERNAL_KERNEL_SRC = "/home/sihokim/projects/raspberrypi/linux"

DEPENDS += "rsync-native"

do_install() {
    oe_runmake -C ${EXTERNAL_KERNEL_SRC} O=${B} ARCH=${ARCH} \
    INSTALL_HDR_PATH=${D}${includedir}/linux-uapi-headers headers_install

    # include/include 구조 해소
    mv ${D}${includedir}/linux-uapi-headers/include/* ${D}${includedir}/linux-uapi-headers/
    rmdir ${D}${includedir}/linux-uapi-headers/include
}

PACKAGES = "${PN}"
FILES:${PN} = "${includedir}/linux-uapi-headers"
