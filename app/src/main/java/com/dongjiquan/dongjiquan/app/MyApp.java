package com.dongjiquan.dongjiquan.app;

import android.app.Application;

import com.orhanobut.hawk.Hawk;
import com.vondear.rxtools.RxUtils;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by wlx on 2017/9/27.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        RxUtils.init(this);
        Hawk.init(this).build();
    }

}
