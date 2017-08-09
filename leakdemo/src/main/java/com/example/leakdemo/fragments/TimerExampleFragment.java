package com.example.leakdemo.fragments;


import android.util.Log;
import android.widget.TextView;

import com.example.leakdemo.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/8.
 */

public class TimerExampleFragment extends Base2Fragment {

    private static final String TAG = "TimerExample";
    @BindView(R.id.tv)
    TextView tv;
    private Disposable disposable;

    @OnClick(R.id.btn)
    public void onClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<? extends Long> getObservable() {
        return Observable.timer(2, TimeUnit.SECONDS);
    }

    private Observer<Long> getObserver(){
        return new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable=d;

            }

            @Override
            public void onNext(Long value) {
                tv.append("on Next value"+value);
                tv.append("\n");
                Log.d(TAG,"onNext"+value);

            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError"+e.getMessage());
                tv.append("\n");
                Log.d(TAG,"onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                tv.append("onComplete");
                tv.append("\n");
                Log.d(TAG,"onComplete");

            }

        };
    }
}
