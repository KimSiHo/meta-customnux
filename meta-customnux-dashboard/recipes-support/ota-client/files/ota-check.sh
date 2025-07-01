#!/bin/sh

LOCKFILE="/tmp/ota-check.lock"
OTA_SERVER="http://@OTA_SERVER@:@OTA_SERVER_PORT@"
MANIFEST_URL="${OTA_SERVER}/manifest.json"
LOCAL_TMP="/tmp"

# 중복 방지
exec 200>$LOCKFILE
flock -n 200 || {
    echo "[ota-check] 이미 실행 중입니다. 중복 실행 방지."
    exit 1
}

echo "[ota-check] OTA 업데이트 확인 중..."

if ! curl -fsSL "$MANIFEST_URL" -o "$LOCAL_TMP/manifest.json"; then
    echo "[ota-check] manifest.json 다운로드 실패"
    exit 1
fi

count=$(jq length "$LOCAL_TMP/manifest.json")
if [ "$count" -eq 0 ]; then
    echo "[ota-check] manifest 항목 없음"
    exit 0
fi

for i in $(seq 0 $((count - 1))); do
    version=$(jq -r ".[$i].version" "$LOCAL_TMP/manifest.json")
    package=$(jq -r ".[$i].package" "$LOCAL_TMP/manifest.json")
    file_name=$(jq -r ".[$i].file_name" "$LOCAL_TMP/manifest.json")
    uboot_env=$(jq -r ".[$i].uboot_env" "$LOCAL_TMP/manifest.json")

    echo "[ota-check] [$package] 버전 $version 확인 중 (U-Boot env: $uboot_env)..."

    current_version=$(fw_printenv "$uboot_env" 2>/dev/null | cut -d '=' -f2)

    if [ "$current_version" = "$version" ]; then
        echo "[ota-check] [$package] 동일 버전($version) 이미 설치됨 - 건너뜀"
        continue
    fi

    echo "[ota-check] [$package] 다운로드 시작: $file_name"
    if curl -fsSL -o "$LOCAL_TMP/$file_name" "$OTA_SERVER/$file_name"; then
        echo "[ota-check] [$package] 설치 중..."
        if swupdate -i "$LOCAL_TMP/$file_name" -v; then
            fw_setenv "$uboot_env" "$version"
            echo "[ota-check] [$package] 설치 완료 및 U-Boot 환경변수 갱신"
        else
            echo "[ota-check] [$package] 설치 실패"
        fi
    else
        echo "[ota-check] [$package] 다운로드 실패"
    fi
done

echo "[ota-check] 모든 OTA 작업 완료"
exit 0
