package com.example.leakdemo;

import android.app.Application;

import com.example.leakdemo.volley.MyVolley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/6/29.
 */

public class MyApplication extends Application {
    private MyApplication instance;
    private RefWatcher refWarcher;

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        instance=(MyApplication)getApplicationContext();
        refWarcher=LeakCanary.install(this);

        MyVolley.init(this);

        Timber.plant(new Timber.DebugTree());

    }
}
