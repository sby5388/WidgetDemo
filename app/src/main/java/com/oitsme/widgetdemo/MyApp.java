package com.oitsme.widgetdemo;

import android.app.Application;

/**
 * Created by zhpan on 2018/1/26.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
    }
}
