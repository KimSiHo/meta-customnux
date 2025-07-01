SUMMARY = "Override multi-user.target with custom dependencies"
DESCRIPTION = "Adds network-online.target as dependency of multi-user.target"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
    file://multi-user-target-override.conf \
    file://remote-fs-target-override.conf \
    file://network-pre-target-override.conf \
"

S = "${WORKDIR}"

inherit systemd

do_install() {
    install -d ${D}${systemd_system_unitdir}/multi-user.target.d
    install -m 0644 ${WORKDIR}/multi-user-target-override.conf ${D}${systemd_system_unitdir}/multi-user.target.d/override.conf

    install -d ${D}${systemd_system_unitdir}/remote-fs.target.d
    install -m 0644 ${WORKDIR}/remote-fs-target-override.conf ${D}${systemd_system_unitdir}/remote-fs.target.d/override.conf

    install -d ${D}${systemd_system_unitdir}/network-pre.target.d
    install -m 0644 ${WORKDIR}/network-pre-target-override.conf ${D}${systemd_system_unitdir}/network-pre.target.d/override.conf
}

FILES:${PN} += " \
    ${systemd_system_unitdir}/multi-user.target.d/ \
    ${systemd_system_unitdir}/remote-fs.target.d/ \
    ${systemd_system_unitdir}/network-pre.target.d/ \
"
