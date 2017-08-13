package com.example.leakdemo.fragments;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.AsyncSubject;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class AsyncSubjectExample extends Base3Fragment{
    @Override
    public void doSomeClick() {

        AsyncSubject<Integer> subject=AsyncSubject.create();


        subject.subscribe(getObserver("First:"));

        subject.onNext(1);
        subject.onNext(3);
        subject.onNext(4);
        subject.onNext(5);

        subject.subscribe(getObserver("Second:"));

        subject.onNext(7);
        subject.onComplete();

    }

    private Observer<? super Integer> getObserver(final String s) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addText(s+"onSubScribe"+d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                addText(s+"onNext"+integer);

            }

            @Override
            public void onError(Throwable e) {
                addText(s+"onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addText(s+"onCOmplete");

            }
        };
    }
}
