package com.example.leakdemo.fragments;


import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class DelayExmapke extends Base3Fragment{
    @Override
    public void doSomeClick() {
        getObservable().delay(2, TimeUnit.SECONDS)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    private Observable<String> getObservable() {
        return Observable.just("Amit");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                addText(" onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String value) {
                addText(" onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                addText(" onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                addText(" onComplete");
            }
        };
    }
}
