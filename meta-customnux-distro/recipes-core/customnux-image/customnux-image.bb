SUMMARY = "custom image for lunux kernel develop"

inherit customnux-base-image

LINGUAS_KO_KR = "ko-kr"
LINGUAS_EN_US = "en-us"
IMAGE_LINGUAS = "${LINGUAS_KO_KR} ${LINGUAS_EN_US}"
IMAGE_OVERHEAD_FACTOR = "1.3"

# https://docs.yoctoproject.org/4.0.23/ref-manual/features.html#image-features
IMAGE_FEATURES = " \
    splash \
    package-management \
    ssh-server-openssh \
    tools-sdk \
    debug-tweaks \
    x11 \
    x11-base \
    x11-sato \
"
IMAGE_FEATURES:qemuarm64-custom = " \
    package-management \
"

IMAGE_INSTALL += " \
    packagegroup-linux-utils \
    kernel-modules \
    opkg \
    bash \
    linux-uapi-headers \
"

inherit extrausers
# todo  개발 끝나고 EXTRA_USERS_PARAMS 로 옮기기
# root: test1234 / great: great1234
# usermod -p '\$6\$3x8A4KPgZQepSqOD\$5F4Kh.SvIVSSQobCKfppxEzE0dSJo5IvPj8JRoREtUwi8wZLqnQK5tikxv/Pd8ex3Rzgdc7A6jobCLDMc4lNT1' root;
# usermod -p '\$6\$Czwb0vsahRPrqU62\$czEC5cuLL03lFO5Wopd0j85wBsiTCcOJG9jHB.ecCOXkSiCK9ItbWdBZHN.JETINexTyd4s07MSpW1LV73mJS1' great;
EXTRA_USERS_PARAMS = " \
    groupadd greatgroup; \
    useradd -s /bin/bash -g greatgroup -p '' great; \
"

# postprocess commands
add_commands() {
    [ ! -L ${IMAGE_ROOTFS}/bin/lsmod ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/lsmod
    [ ! -L ${IMAGE_ROOTFS}/bin/insmod ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/insmod
    [ ! -L ${IMAGE_ROOTFS}/bin/rmmod ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/rmmod
    [ ! -L ${IMAGE_ROOTFS}/bin/modinfo ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/modinfo
    [ ! -L ${IMAGE_ROOTFS}/bin/modprobe ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/modprobe
    [ ! -L ${IMAGE_ROOTFS}/bin/depmode ] && ln -s /bin/kmod ${IMAGE_ROOTFS}/bin/depmod
}

update_setting_files() {
    sed -i 's#/bin/sh#/bin/bash#g' ${IMAGE_ROOTFS}/etc/passwd
#    sed -i '/^SYSLOG_SU_ENAB/d' ${IMAGE_ROOTFS}/etc/login.defs
#    sed -i '/^SYSLOG_SG_ENAB/d' ${IMAGE_ROOTFS}/etc/login.defs
}

ROOTFS_POSTPROCESS_COMMAND += " \
    add_commands; \
    update_setting_files; \
"
