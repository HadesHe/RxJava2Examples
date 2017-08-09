package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.example.leakdemo.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/9.
 */

public class CompletableObserverExample extends Base2Fragment {

    private static final java.lang.CharSequence TAG = "CompletableObserverExample";
    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onClick(){
        Completable completable=Completable.timer(1000, TimeUnit.MILLISECONDS);
        completable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getCompletabelObserver());


    }

    private CompletableObserver getCompletabelObserver() {
        return new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                tv.append(" onSubScribe: "+d.isDisposed());

            }

            @Override
            public void onComplete() {
                tv.append(" onComplete");
                tv.append("\n");


            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError"+e.getMessage());
                tv.append("\n");

            }
        };
    }
}
