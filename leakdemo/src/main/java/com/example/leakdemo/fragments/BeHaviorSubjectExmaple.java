package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class BeHaviorSubjectExmaple extends Base3Fragment {
    @Override
    public void doSomeClick() {

        BehaviorSubject<Integer> source=BehaviorSubject.create();

        source.subscribe(getObserver("First"));
        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.subscribe(getObserver("Second"));

        source.onNext(4);
        source.onComplete();

    }

    private Observer<? super Integer> getObserver(final String s) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addText("onSubscribe"+s+d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                addText("onNext"+s+integer);

            }

            @Override
            public void onError(Throwable e) {
                addText("onErrot"+s+e.getMessage());

            }

            @Override
            public void onComplete() {
                addText("onCOmplete"+s);

            }
        };
    }
}
