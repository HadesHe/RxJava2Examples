package com.example.leakdemo.ui;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
/**
 * Created by zhanghehe on 2017/8/27.
 */

public class RxBus {

    public RxBus(){}

    private PublishSubject<Object> bus=PublishSubject.create();

    public void send(Object o){
        bus.onNext(o);
    }

    public Observable<Object> toObservable(){
        return  bus;
    }

    public boolean hasObservers(){
        return bus.hasObservers();
    }
}
