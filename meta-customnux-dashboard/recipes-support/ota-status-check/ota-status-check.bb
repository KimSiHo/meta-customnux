SUMMARY = "SWUpdate 상태 감지 후처리 서비스"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://ota-status-check.service \
    file://ota-status-check.path \
    file://ota-status-check.sh \
"

inherit systemd
SYSTEMD_SERVICE:${PN} = "ota-status-check.path"
SYSTEMD_AUTO_ENABLE = "enable"

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ota-status-check.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/ota-status-check.path ${D}${systemd_system_unitdir}/

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/ota-status-check.sh ${D}${bindir}/
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/ota-status-check.service \
    ${systemd_system_unitdir}/ota-status-check.path \
    ${bindir}/ota-status-check.sh \
"
