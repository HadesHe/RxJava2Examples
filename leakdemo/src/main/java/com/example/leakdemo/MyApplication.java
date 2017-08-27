package com.example.leakdemo;

import android.app.Application;

import com.example.leakdemo.model.Events;
import com.example.leakdemo.ui.RxBus;
import com.example.leakdemo.volley.MyVolley;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/6/29.
 */

public class MyApplication extends Application {
    private static MyApplication instance;
    private RefWatcher refWatcher;
    private RxBus bus;

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

        bus=new RxBus();

        instance=(MyApplication)getApplicationContext();
        refWatcher=LeakCanary.install(this);

        MyVolley.init(this);

        Timber.plant(new Timber.DebugTree());

    }

    public RxBus bus(){
        return bus;
    }

    public void sendAutoEvent(){
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        bus.send(new Events.AutoEvent());
                    }
                });
    }


}
