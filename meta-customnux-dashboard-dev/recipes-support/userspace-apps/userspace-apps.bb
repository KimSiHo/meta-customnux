DESCRIPTION = "kernel develop utility files"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${TARGET_ARCH}"

S = "${WORKDIR}"
SRC_URI = " \
    file://scripts/get_ftrace.sh \
    file://scripts/set_ftrace.sh \
    file://process/create/create.c \
    file://process/create/Makefile \
    file://process/create/set_ftrace.sh \
    file://process/gdb-debug/Makefile \
    file://process/gdb-debug/test_fork.c \
    file://device-driver/scull/scull_user.h \
    file://device-driver/scull/test.c \
    file://device-driver/spi/basic-test.c \
    file://device-driver/spi/dma-benchmark-test.sh \
    file://device-driver/spi/full-duplex.c \
    file://device-driver/spi/half-duplex.c \
    file://device-driver/can/can-raw.c \
    file://device-driver/can/can-raw-stats.c \
    file://device-driver/can/Makefile \
    file://device-driver/watchdog/watchdog-userspace.c \
    file://device-driver/watchdog/Makefile \
"

do_compile() {
    HEADER_DIR="${S}/device-driver"
    mkdir -p ${B}/device-driver/scull

    echo ${CC} ${CFLAGS} ${LDFLAGS} -I${HEADER_DIR} -o ${B}/device-driver/scull/test ${S}/device-driver/scull/test.c
    ${CC} ${CFLAGS} ${LDFLAGS} -I${HEADER_DIR} -o ${B}/device-driver/scull/test ${S}/device-driver/scull/test.c
}

do_install() {
    install -d ${D}/home/${DEFAULT_USER}/scripts
    install -m 0755 ${S}/scripts/set_ftrace.sh ${D}/home/${DEFAULT_USER}/scripts
    install -m 0755 ${S}/scripts/get_ftrace.sh ${D}/home/${DEFAULT_USER}/scripts

    install -d ${D}/home/${DEFAULT_USER}/project/process/create
    install -m 0755 ${S}/process/create/create.c ${D}/home/${DEFAULT_USER}/project/process/create
    install -m 0755 ${S}/process/create/Makefile ${D}/home/${DEFAULT_USER}/project/process/create
    install -m 0755 ${S}/process/create/set_ftrace.sh ${D}/home/${DEFAULT_USER}/project/process/create

    install -d ${D}/home/${DEFAULT_USER}/project/process/gdb-debug
    install -m 0755 ${S}/process/gdb-debug/test_fork.c ${D}/home/${DEFAULT_USER}/project/process/gdb-debug
    install -m 0755 ${S}/process/gdb-debug/Makefile ${D}/home/${DEFAULT_USER}/project/process/gdb-debug


    install -d ${D}/home/${DEFAULT_USER}/project/device-driver/scull
    install -d ${D}/usr/include/scull
    install -m 0755 ${S}/device-driver/scull/test.c ${D}/home/${DEFAULT_USER}/project/device-driver/scull
    install -m 0644 ${S}/device-driver/scull/scull_user.h ${D}/usr/include/scull
    install -m 0755 ${B}/device-driver/scull/test ${D}/home/${DEFAULT_USER}/project/device-driver/scull

    install -d ${D}/home/${DEFAULT_USER}/project/device-driver/spi
    install -m 0755 ${S}/device-driver/spi/basic-test.c ${D}/home/${DEFAULT_USER}/project/device-driver/spi/basic-test.c
    install -m 0755 ${S}/device-driver/spi/dma-benchmark-test.sh ${D}/home/${DEFAULT_USER}/project/device-driver/spi/dma-benchmark-test.sh
    install -m 0755 ${S}/device-driver/spi/full-duplex.c ${D}/home/${DEFAULT_USER}/project/device-driver/spi/full-duplex.c
    install -m 0755 ${S}/device-driver/spi/half-duplex.c ${D}/home/${DEFAULT_USER}/project/device-driver/spi/half-duplex.c

    install -d ${D}/home/${DEFAULT_USER}/project/device-driver/can
    install -m 0755 ${S}/device-driver/can/can-raw.c ${D}/home/${DEFAULT_USER}/project/device-driver/can/can-raw.c
    install -m 0755 ${S}/device-driver/can/can-raw-stats.c ${D}/home/${DEFAULT_USER}/project/device-driver/can/can-raw-stats.c
    install -m 0755 ${S}/device-driver/can/Makefile ${D}/home/${DEFAULT_USER}/project/device-driver/can/Makefile

    install -d ${D}/home/${DEFAULT_USER}/project/device-driver/watchdog
    install -m 0755 ${S}/device-driver/watchdog/Makefile ${D}/home/${DEFAULT_USER}/project/device-driver/watchdog/Makefile
    install -m 0755 ${S}/device-driver/watchdog/watchdog-userspace.c ${D}/home/${DEFAULT_USER}/project/device-driver/watchdog/watchdog-userspace.c
}

FILES:${PN} += " \
    /home/${DEFAULT_USER}/scripts \
    /home/${DEFAULT_USER}/project \
"
FILES_${PN}-dev += "/usr/include/scull"

RDEPENDS:${PN} = "bash"
