SUMMARY = "Service to bring up can0 interface at boot"
DESCRIPTION = "Systemd service that runs 'ip link set can0 up type can bitrate 500000'"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://can0-up.service"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "can0-up.service"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/can0-up.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/can0-up.service"
