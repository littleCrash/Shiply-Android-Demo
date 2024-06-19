package com.tencent.rfix.sample.com.app;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.tencent.rfix.anno.ApplicationLike;
import com.tencent.rfix.entry.DefaultRFixApplicationLike;
import com.tencent.rfix.entry.RFixApplicationLike;
import com.tencent.rfix.lib.RFixInitializer;
import com.tencent.rfix.lib.RFixParams;
import com.tencent.rfix.loader.entity.RFixLoadResult;
import com.tencent.rfix.loader.log.RFixLog;
import com.tencent.rfix.sample.com.log.CustomRFixLog;

@ApplicationLike(application = ".SampleLikeApplication")
public class SampleApplicationLike extends DefaultRFixApplicationLike {

    private static final String TAG = "RFix.SampleApplicationLike";

    public SampleApplicationLike(Application application, RFixLoadResult loadResult) {
        super(application, loadResult);
    }

    @Override
    public void onBaseContextAttached(Context base) {
        super.onBaseContextAttached(base);

        // 初始化RFix组件
        initRFixAsync(this);
    }

    public static void initRFix(RFixApplicationLike applicationLike) {
        Log.d(TAG, "initRFix...");

        // 初始化日志接口
        RFixLog.setLogImpl(new CustomRFixLog());

        // 构造RFix业务参数
        RFixParams params = new RFixParams("760f8bca6b", "82787958-a6cc-4408-9897-814a0e7afd21")
                .setDeviceId("000000")
                .setDeviceManufacturer(Build.MANUFACTURER)
                .setDeviceModel(Build.MODEL)
                .setUserId("123456")
                .setCustomProperty("appid", "1000");

        // 初始化RFix组件
        RFixInitializer.initialize(applicationLike, params);
    }

    public static void initRFixAsync(RFixApplicationLike applicationLike) {
        Log.d(TAG, "initRFixAsync...");

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Log.e(TAG, "initRFixAsync fail!", e);
            }

            initRFix(applicationLike);
        });
        thread.start();
    }
}
