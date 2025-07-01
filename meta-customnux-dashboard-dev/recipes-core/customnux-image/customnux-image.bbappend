UTILITY_TOOLS = " \
    fbgrab \
    tmux \
    tree \
    bash \
    curl \
    numlockx \
    tmate \
    app-settings \
    connman-client \
    bpftrace \
"

NETWORK_TOOLS = " \
    nfs-utils \
    nfs-mount \
    ethtool \
"

DEBUG_TOOLS = " \
    gdb \
    strace \
    systemd-analyze \
"

HARDWARE_TOOLS = " \
    i2c-tools \
    v4l-utils \
    alsa-utils \
    libgpiod \
    libgpiod-tools \
"

IN_DEVELOP_INSTALL += " \
    scull \
    userspace-apps \
    userspace-apps-dev \
"

IMAGE_INSTALL += " \
    ${UTILITY_TOOLS} \
    ${NETWORK_TOOLS} \
    ${DEBUG_TOOLS} \
    ${HARDWARE_TOOLS} \
    ${IN_DEVELOP_INSTALL} \
"
