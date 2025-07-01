SUMMARY = "Linux kernel tools"
DESCRIPTION = "Installs Linux kernel tools from the Linux kernel source tree"

SECTION = "kernel"

LICENSE = "GPL-2.0-only"

inherit linux-kernel-base

do_fetch[noexec] = "1"
do_unpack[noexec] = "1"
do_patch[noexec] = "1"
do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${STAGING_KERNEL_DIR}"
B = "${STAGING_KERNEL_BUILDDIR}"

# Ensure this executes after kernel unpacking and installation
# This gives access to ${S} with full source tree and ${B} with built artifacts
do_install[depends] += "virtual/kernel:do_shared_workdir"
do_install[depends] += "virtual/kernel:do_install"

KERNEL_TOOL_PATH = "${datadir}/kernel-tools/workqueue"

do_install() {
    install -d ${D}${KERNEL_TOOL_PATH}
    cp -v ${S}/tools/workqueue/*.py ${D}${KERNEL_TOOL_PATH}/
}

FILES:${PN} += "${KERNEL_TOOL_PATH}"
