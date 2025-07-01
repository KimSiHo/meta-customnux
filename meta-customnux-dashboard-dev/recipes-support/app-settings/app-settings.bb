DESCRIPTION = "custom settings"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit allarch

S = "${WORKDIR}"
SRC_URI = " \
    file://vim/vimrc \
    file://bash/bashrc \
    file://bash/bash.bashrc \
    file://bash/custom-profile.sh \
"

do_install() {
  install -d ${D}/etc/profile.d/
  install -m 0644 ${S}/bash/custom-profile.sh ${D}/etc/profile.d

  # vim 설정
  install -d ${D}/home/root/.vim/
  install -m 0644 ${S}/vim/vimrc ${D}/home/root/.vim/
  install -d ${D}/home/${DEFAULT_USER}/.vim/
  install -m 0644 ${S}/vim/vimrc ${D}/home/${DEFAULT_USER}/.vim/

  # bash 설정
  install -d ${D}/etc/
  install -m 0644 ${S}/bash/bash.bashrc ${D}/etc
  install -d ${D}/home/root/
  install -m 0644 ${S}/bash/bashrc ${D}/home/root/.bashrc
  install -d ${D}/home/${DEFAULT_USER}/
  install -m 0644 ${S}/bash/bashrc ${D}/home/${DEFAULT_USER}/.bashrc
}

FILES:${PN} += " \
    /etc/profile.d \
    /home/root/ \
    /home/${DEFAULT_USER}/ \
"
