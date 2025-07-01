SUMMARY = "Linux kernel from kernel.org git repositoy"
SECTION = "kernel"
LICENSE = "GPL-2.0-only"
LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

PACKAGE_ARCH = "${MACHINE_ARCH}"

inherit kernel
inherit kernel-yocto
inherit externalsrc

EXTERNALSRC = "/home/sihokim/projects/linux"
KBUILD_DEFCONFIG = "defconfig"
KCONFIG_MODE = "alldefconfig"
COMPATIBLE_MACHINE = "raspberrypi4-64|qemuarm64-custom"

LINUX_VERSION ?= "6.11.11-0"
LINUX_VERSION_EXTENSION  = "-generic"
PV = "${LINUX_VERSION}"

DEPENDS += "coreutils-native"

PROVIDES = "virtual/kernel"
