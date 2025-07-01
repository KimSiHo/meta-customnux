FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI += "file://99-hdmi-default.pa"

do_install:append() {
    install -d ${D}${sysconfdir}/pulse/default.pa.d
    install -m 0644 ${WORKDIR}/99-hdmi-default.pa ${D}${sysconfdir}/pulse/default.pa.d/99-hdmi-default.pa
}
