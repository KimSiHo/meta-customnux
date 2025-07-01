SUMMARY = "Systemd service to auto-mount NFS share for capture"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "file://nfs-mount.service"

S = "${WORKDIR}"

inherit systemd

SYSTEMD_SERVICE:${PN} = "nfs-mount.service"
SYSTEMD_AUTO_ENABLE = "enable"
NFS_SERVER = "192.168.45.96"

do_configure:prepend() {
    sed -i "s|@NFS_SERVER@|${NFS_SERVER}|" ${WORKDIR}/nfs-mount.service
}

do_install() {
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/nfs-mount.service ${D}${systemd_system_unitdir}/
}

FILES:${PN} += "${systemd_system_unitdir}/nfs-mount.service"
