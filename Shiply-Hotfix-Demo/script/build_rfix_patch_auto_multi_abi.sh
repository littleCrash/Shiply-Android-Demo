# 是否构建Release版本(true/false)
BUILD_RELEASE=true

# 构建BaseApk
build_base_apk() {
  ./gradlew -s clean

  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :app:assembleDebug -PRFIX_VERIFY_CASE='Base'
  else
    ./gradlew -s :app:assembleRelease -PRFIX_VERIFY_CASE='Base'
  fi
}

# 构建PatchApk
build_patch_apk() {
  ./gradlew -s clean

  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :app:assembleDebug -PRFIX_VERIFY_CASE='Patch'
  else
    ./gradlew -s :app:assembleRelease -PRFIX_VERIFY_CASE='Patch'
  fi
}

# 构建补丁
build_patch() {
  if [ "$BUILD_RELEASE" = false ]; then
    ./gradlew -s :app:RFixBuildDebug -PRFIX_MULTI_ARCH=true -PRFIX_PATCH_TYPE='Tinker'

    cp -f app/RFix/rfixPatch/debug/*_Tinker_signed.apk app/RFix/patch_tinker.apk
    cp -f app/RFix/rfixPatch/debug/*_Tinker_signed-0.apk app/RFix/patch_tinker-0.apk
    cp -f app/RFix/rfixPatch/debug/*_Tinker_signed-1.apk app/RFix/patch_tinker-1.apk
  else
    ./gradlew -s :app:RFixBuildRelease -PRFIX_MULTI_ARCH=true -PRFIX_PATCH_TYPE='Tinker'

    cp -f app/RFix/rfixPatch/release/*_Tinker_signed.apk app/RFix/patch_tinker.apk
    cp -f app/RFix/rfixPatch/release/*_Tinker_signed-0.apk app/RFix/patch_tinker-0.apk
    cp -f app/RFix/rfixPatch/release/*_Tinker_signed-1.apk app/RFix/patch_tinker-1.apk
  fi

  # 返回成功，避免流水线执行出现失败
  return 0
}


chmod +x gradlew

# 1.清除构建缓存
rm -rf app/autoBackup
rm -rf app/RFix

mkdir app/autoBackup
mkdir app/RFix

# 2.构建基础APK
rm -rf app/autoBackup/*
build_base_apk

cp -f app/autoBackup/*-v7a.apk app/RFix/old.apk
cp -f app/autoBackup/*-v8a.apk app/RFix/old64.apk
cp -f app/autoBackup/*-mapping.txt app/RFix/old_mapping.txt
cp -f app/autoBackup/*-R.txt app/RFix/old_R.txt

# 3. 构建修复APK
# 构造补丁修改
rm -rf app/autoBackup/*
build_patch_apk

cp -f app/autoBackup/*-v7a.apk app/RFix/new.apk
cp -f app/autoBackup/*-v8a.apk app/RFix/new64.apk
cp -f app/autoBackup/*-mapping.txt app/RFix/new_mapping.txt

# 4.构建补丁包
rm -rf app/RFix/rfixPatch
build_patch

# 5.安装补丁包
#adb install app/RFix/old.apk
#adb push app/RFix/patch_tinker.apk /sdcard/RFix-patch.apk