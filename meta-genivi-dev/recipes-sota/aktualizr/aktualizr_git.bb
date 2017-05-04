SUMMARY = "Aktualizr SOTA Client"
DESCRIPTION = "SOTA Client written in C++"
HOMEPAGE = "https://github.com/advancedtelematic/aktualizr"
SECTION = "base"

LICENSE = "MPL-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=9741c346eef56131163e13b9db1241b3"

inherit cmake systemd

S = "${WORKDIR}/git"

SRCREV = "aa74875684bac2ed62eb759e24d88b4fef166d9d"

SRC_URI = " \
    git://github.com/advancedtelematic/aktualizr;branch=bug/176/rvi_void \
    file://aktualizr.service \
    "

SYSTEMD_SERVICE_${PN} = "aktualizr.service"

DEPENDS = "boost curl openssl jansson dbus"

EXTRA_OECMAKE = "-DWARNING_AS_ERROR=OFF -DCMAKE_BUILD_TYPE=Release -DBUILD_TESTS=OFF -DBUILD_GENIVI=ON"

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${WORKDIR}/build/aktualizr ${D}${bindir}/
    install -d ${D}${sysconfdir}/dbus-1/system.d/
    install -c ${S}/dbus/org.genivi.SotaClient.conf ${D}${sysconfdir}/dbus-1/system.d
    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${WORKDIR}/aktualizr.service ${D}${systemd_system_unitdir}
}

FILES_${PN} = " \
    ${sysconfdir}/dbus-1/system.d/org.genivi.SotaClient.conf \
    ${bindir}/aktualizr \
    ${systemd_system_unitdir}/aktualizr.service \
    "
