package com.example.leakdemo;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by zhanghehe on 2017/6/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        LeakCanary.install(this);
    }
}
