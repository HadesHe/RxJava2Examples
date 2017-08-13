package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class ReplaySubjectExample extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onClick(){
        PublishSubject<Integer> source=PublishSubject.create();

        source.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        source.subscribe(getSecondObserver());
    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addTextToTv("First onSubscribe: "+ d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                addTextToTv("Fist onNext:"+ integer);

            }

            @Override
            public void onError(Throwable e) {
                addTextToTv("First onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addTextToTv("Second onComplete");

            }
        };
    }

    private void addTextToTv(String s) {
        tv.append(s);
        tv.append("\n");
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addTextToTv("Second OnSubscribe"+d.isDisposed());

            }

            @Override
            public void onNext(Integer integer) {
                addTextToTv("Second Onnextvalue"+integer);

            }

            @Override
            public void onError(Throwable e) {
                addTextToTv("Second OnError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addTextToTv("Second onComplete");

            }
        };
    }
}
