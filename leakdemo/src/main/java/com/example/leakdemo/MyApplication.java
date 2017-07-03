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
    private static MyApplication instance;
    private RefWatcher refWatcher;

    public static MyApplication get(){
        return instance;
    }

    public static RefWatcher getRefWatcher(){
        return MyApplication.get().refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(LeakCanary.isInAnalyzerProcess(this)){
            return;
        }

        instance=(MyApplication)getApplicationContext();
        refWatcher=LeakCanary.install(this);

        MyVolley.init(this);

        Timber.plant(new Timber.DebugTree());

    }
}
