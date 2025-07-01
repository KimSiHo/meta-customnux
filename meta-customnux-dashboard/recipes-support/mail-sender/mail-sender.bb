DESCRIPTION = "My Java application"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

PACKAGE_ARCH = "${TARGET_ARCH}"

S = "${WORKDIR}"
SRC_URI = " \
    file://ResourceWatcher.java \
    https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar;unpack=0;name=javax-mail-jar \
    https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar;unpack=0;name=activate-jar \
"
SRC_URI[javax-mail-jar.sha256sum] = "45b515e7104944c09e45b9c7bb1ce5dff640486374852dd2b2e80cc3752dfa11"
SRC_URI[activate-jar.md5sum] = "46a37512971d8eca81c3fcf245bf07d2"

# todo compile 하고 서비스 등록 자동화
# do_compile() {
#     export JAVA_HOME="${STAGING_DIR_TARGET}${libdir}/jvm/openjdk-8"
#     JAVAC="${JAVA_HOME}/bin/javac"
#     JAVAMAIL_JAR="${S}/javax.mail-1.6.2.jar"
#
#     ${JAVAC} -classpath ${JAVAMAIL_JAR} -d ${S} ${S}/MailSender.java
# }

do_install() {
  install -d ${D}${bindir}
  install -m 0755 ${S}/ResourceWatcher.java ${D}${bindir}/
  # install -m 0755 ${S}/MailSender.class ${D}${bindir}/

  install -d ${D}${libdir}/jvm/openjdk-8/lib
  install -m 0644 ${S}/javax.mail-1.6.2.jar ${D}${libdir}/jvm/openjdk-8/lib
  install -m 0644 ${S}/activation-1.1.1.jar ${D}${libdir}/jvm/openjdk-8/lib
}

FILES:${PN} += "${libdir}/jvm/openjdk-8/lib"
