SUMMARY = "User Dashboard App"
LICENSE = "CLOSED"

require project-variables.inc

SRC_URI = " \
    file://camera/camera_player.py \
    file://dashboard/main.py \
    file://dashboard/monitoring_page.py \
    file://dashboard/peripheral_devices_page.py \
    file://dashboard/system_page.py \
    file://dashboard/undecided_page.py \
    file://distance-sensor/distance_sensor.py \
    file://misc/uart_test.py \
    file://music/alert.wav \
    file://music/emergency.wav \
    file://music/notification.wav \
    file://music/sound_player.py \
    file://user-dashboard.service \
"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "user-dashboard.service"
# SYSTEMD_AUTO_ENABLE:${PN} = "enable"
SYSTEMD_AUTO_ENABLE:${PN} = "disable"

do_install() {
    install -d ${D}${DASHBOARD_PROJECT_DIR}
    install -m 0755 ${WORKDIR}/dashboard/main.py ${D}${DASHBOARD_PROJECT_DIR}/main.py
    install -m 0755 ${WORKDIR}/dashboard/monitoring_page.py ${D}${DASHBOARD_PROJECT_DIR}/monitoring_page.py
    install -m 0755 ${WORKDIR}/dashboard/peripheral_devices_page.py ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices_page.py
    install -m 0755 ${WORKDIR}/dashboard/system_page.py ${D}${DASHBOARD_PROJECT_DIR}/system_page.py
    install -m 0755 ${WORKDIR}/dashboard/undecided_page.py ${D}${DASHBOARD_PROJECT_DIR}/undecided_page.py

    install -d ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices
    install -m 0755 ${WORKDIR}/distance-sensor/distance_sensor.py ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices/distance_sensor.py
    install -m 0755 ${WORKDIR}/misc/uart_test.py ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices/uart_test.py
    install -m 0755 ${WORKDIR}/music/sound_player.py ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices/sound_player.py
    install -m 0755 ${WORKDIR}/camera/camera_player.py ${D}${DASHBOARD_PROJECT_DIR}/peripheral_devices/camera_player.py

    install -d ${D}${MUSIC_DIR}
    install -m 0755 ${WORKDIR}/music/alert.wav ${D}${MUSIC_DIR}/alert.wav
    install -m 0755 ${WORKDIR}/music/emergency.wav ${D}${MUSIC_DIR}/emergency.wav
    install -m 0755 ${WORKDIR}/music/notification.wav ${D}${MUSIC_DIR}/notification.wav

    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/user-dashboard.service ${D}${systemd_unitdir}/system
}

FILES:${PN} += " \
    ${DASHBOARD_PROJECT_DIR} \
    ${MUSIC_DIR} \
    ${systemd_unitdir}/system \
"
