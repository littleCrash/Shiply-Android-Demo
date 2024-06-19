package com.tencent.rfix.sample.com.log;

import android.util.Log;
import com.tencent.rfix.loader.log.IRFixLog;

public class CustomRFixLog implements IRFixLog {

    @Override
    public void v(String tag, String msg) {
        Log.v(tag, msg);
    }

    @Override
    public void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    @Override
    public void i(String tag, String msg) {
        Log.i(tag, msg);
    }

    @Override
    public void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    @Override
    public void w(String tag, String msg, Throwable e) {
        Log.w(tag, msg, e);
    }

    @Override
    public void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    @Override
    public void e(String tag, String msg, Throwable e) {
        Log.e(tag, msg, e);
    }
}
