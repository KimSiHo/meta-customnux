DESCRIPTION = "scull device driver utility for register device driver"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

S = "${WORKDIR}"
SRC_URI = " \
    file://scull.conf \
    file://scull.init \
"

do_install() {
  install -d ${D}/home/great/device-driver/scull
  install -m 0755 ${S}/scull.init ${D}/home/great/device-driver/scull

  install -d ${D}/etc
  install -m 0644 ${S}/scull.conf ${D}/etc
}

FILES:${PN} += "/home/great/device-driver"

RDEPENDS:${PN} = "bash"
