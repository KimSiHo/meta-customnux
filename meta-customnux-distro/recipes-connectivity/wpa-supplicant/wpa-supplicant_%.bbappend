FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += " \
    file://wpa_supplicant.conf-sane \
    file://wlan-setup.service \
    file://wlan-setup.sh \
"

do_install:append() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/wlan-setup.sh ${D}${bindir}/wlan-setup.sh

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/wlan-setup.service ${D}${systemd_system_unitdir}/wlan-setup.service
}

inherit systemd
SYSTEMD_SERVICE:${PN} = "wlan-setup.service"
# FIXME 와이파이 연결 안됨. 라즈베리 하드웨어 자체적인 문제가 조금 있어 보임. 연구 필요.
SYSTEMD_AUTO_ENABLE:${PN} = "disable"
