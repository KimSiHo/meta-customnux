SUMMARY = "SWUpdate image bundle"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit swupdate

SRC_URI = " \
    file://sw-description \
    file://post-install.sh \
"

SWUPDATE_IMAGES = "customnux-image"
SWUPDATE_IMAGES_FSTYPES[customnux-image] = ".ext4"
