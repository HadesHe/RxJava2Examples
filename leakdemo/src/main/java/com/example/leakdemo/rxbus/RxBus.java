package com.example.leakdemo.rxbus;

import com.jakewharton.rxrelay2.PublishRelay;
import com.jakewharton.rxrelay2.Relay;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public class RxBus {

    private final Relay<Object> bus= PublishRelay.create().toSerialized();

    public void send(Object o){
        bus.accept(o);
    }

    public Flowable<Object> asFlowable(){
        return bus.toFlowable(BackpressureStrategy.LATEST);
    }

    public boolean hasObservers(){
        return bus.hasObservers();
    }
}
