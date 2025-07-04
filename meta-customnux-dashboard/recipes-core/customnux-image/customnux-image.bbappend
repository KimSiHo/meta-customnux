BASIC_INSTALL = " \
    jq \
    base-files \
    kernel-devsrc \
    kernel-tools \
    kernel-selftest \
"

PYTHON_INSTALL = " \
    python3 \
    python3-pip \
    python3-numpy \
    python3-logging \
    python3-drgn \
"

PYQT5_INSTALL = " \
    python3-pyqt5 \
    qtbase \
    fontconfig \
    ttf-dejavu-sans \
    ttf-dejavu-sans-mono \
    source-han-sans-kr-fonts \
"

CAMERA_INSTALL = " \
    ffmpeg \
    libcamera \
    gstreamer1.0 \
    gstreamer1.0-plugins-base \
    gstreamer1.0-plugins-good \
    gstreamer1.0-plugins-bad \
    gstreamer1.0-plugins-ugly \
    gstreamer1.0-rtsp-server \
    gstreamer1.0-vaapi \
    gstreamer1.0-meta-base \
    opencv \
"

AUDIO_INSTALL = " \
    python3-pyalsaaudio \
"

BLUETOOTH_INSTALL = " \
    bluez5 \
"

CAN_INSTALL = " \
    can-utils \
    can-supports \
"

DASHBOARD_PROJECT_INSTALL = " \
    user-dashboard \
    python3-psutil \
"

OTA_INSTALL = " \
    swupdate \
    ota-status-check \
    ota-client \
"

UBOOT_INSTALL = " \
    u-boot \
    libubootenv \
    libubootenv-bin \
"

FB_INSTALL = " \
    fbida \
"

IMAGE_INSTALL:append = " \
    ${BASIC_INSTALL} \
    ${PYTHON_INSTALL} \
    ${PYQT5_INSTALL} \
    ${CAMERA_INSTALL} \
    ${AUDIO_INSTALL} \
    ${BLUETOOTH_INSTALL} \
    ${CAN_INSTALL} \
    ${DASHBOARD_PROJECT_INSTALL} \
    ${OTA_INSTALL} \
    ${UBOOT_INSTALL} \
    ${FB_INSTALL} \
"

IMAGE_INSTALL:append = "${@bb.utils.contains('DISTRO_EXTRA_RDEPENDS', 'openjdk-8', ' mail-sender', '', d)}"

DEPENDS:append:raspberrypi4-64 = " \
    rpi-bootfiles \
"

WKS_FILE:raspberrypi4-64 = "customnux-image.wks"
