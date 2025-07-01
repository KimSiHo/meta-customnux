FILES:${PN} += "{systemd_system_unitdir}/"
SYSTEMD_AUTO_ENABLE = "enable"

FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
SRC_URI:append = "file://xserver-nodm.service.in"

# disable "blank" (dpms and screen saver)
PACKAGECONFIG = ""
