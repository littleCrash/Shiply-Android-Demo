package com.tencent.rfix.sample.com;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;

import com.tencent.rfix.lib.dev.AbsRFixDevActivity;

public class MainActivity extends AbsRFixDevActivity {

    private static final String TAG = "RFix.MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 请求SDCard读取权限
        verifyStoragePermissions(this);
    }

    /**
     * 检查是否有SDCard的读写权限
     */
    protected void verifyStoragePermissions(Activity activity) {
        final String PERMISSIONS_READ_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";
        final String PERMISSIONS_WRITE_STORAGE = "android.permission.WRITE_EXTERNAL_STORAGE";

        int permission = ActivityCompat.checkSelfPermission(activity, PERMISSIONS_WRITE_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            final String[] PERMISSIONS_STORAGE = {PERMISSIONS_READ_STORAGE, PERMISSIONS_WRITE_STORAGE};
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 0);
        }
    }
}