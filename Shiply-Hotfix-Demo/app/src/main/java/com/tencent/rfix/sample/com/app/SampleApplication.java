package com.tencent.rfix.sample.com.app;

import android.app.Application;
import android.content.Context;

import com.tencent.rfix.anno.ApplicationProxy;
import com.tencent.rfix.entry.DefaultRFixApplicationLike;
import com.tencent.rfix.entry.RFixApplicationLike;

@ApplicationProxy(application = ".SampleProxyApplication")
public class SampleApplication extends Application {

    private static final String TAG = "RFix.SampleApplication";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // 初始化RFix组件
        final RFixApplicationLike applicationLike = DefaultRFixApplicationLike.createApplicationLike(this);
        SampleApplicationLike.initRFixAsync(applicationLike);
    }
}
