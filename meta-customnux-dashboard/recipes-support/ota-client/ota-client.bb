SUMMARY = "SWUpdate OTA 클라이언트 - 서버에서 .swu 파일 자동 확인 및 설치"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://ota-check.service \
    file://ota-check.timer \
    file://ota-check.sh \
"

inherit systemd
SYSTEMD_SERVICE:${PN} = "ota-check.timer"
SYSTEMD_AUTO_ENABLE:${PN} = "enable"

do_configure:prepend() {
    sed -i "s|@OTA_SERVER@|${OTA_SERVER}|" ${WORKDIR}/ota-check.sh
    sed -i "s|@OTA_SERVER_PORT@|${OTA_SERVER_PORT}|" ${WORKDIR}/ota-check.sh
}

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/ota-check.service ${D}${systemd_system_unitdir}/
    install -m 0644 ${WORKDIR}/ota-check.timer ${D}${systemd_system_unitdir}/

    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/ota-check.sh ${D}${bindir}/
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/ota-check.service \
    ${systemd_system_unitdir}/ota-check.timer \
    ${bindir}/ota-check.sh \
"
