SUMMARY = "utils package group"

inherit packagegroup

RDEPENDS:${PN} = " \
    vim \
    procps \
    pciutils \
    usbutils \
    crash kexec-tools \
    makedumpfile \
"
