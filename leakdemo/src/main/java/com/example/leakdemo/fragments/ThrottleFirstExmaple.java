package com.example.leakdemo.fragments;


import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class ThrottleFirstExmaple extends Base3Fragment {
    @Override
    public void doSomeClick() {
        getObservable()
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observer<? super Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addText("onSubscribe"+d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                addText("onNext"+integer);

            }

            @Override
            public void onError(Throwable e) {
                addText("onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addText("onComplete");

            }
        };
    }

    private Observable<Integer> getObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Thread.sleep(0);
                e.onNext(1);
                e.onNext(2);
                Thread.sleep(505);
                e.onNext(3);
                Thread.sleep(99);
                e.onNext(4);
                Thread.sleep(100);
                e.onNext(5);
                e.onNext(6);
                Thread.sleep(305);
                e.onNext(7);
                Thread.sleep(510);
                e.onComplete();
            }
        });
    }
}
