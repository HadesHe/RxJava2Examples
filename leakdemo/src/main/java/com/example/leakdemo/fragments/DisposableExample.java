package com.example.leakdemo.fragments;


import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;

import com.example.leakdemo.R;

import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/2.
 */

public class DisposableExample extends Base2Fragment {

    private static final String TAG = DisposableExample.class.getSimpleName();
    @BindView(R.id.tv)
    TextView tv;

    private final CompositeDisposable disposables=new CompositeDisposable();
    @Override
    public int layoutResId() {
        return R.layout.fragment_example;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        disposables.clear();
    }

    @OnClick(R.id.btn)
    public void onBtnClick(){
        disposables.add(sampleObservable().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                tv.append(" onNext : value: "+s);
                tv.append("\n");
                Log.d(TAG," onNext value: "+s);

            }

            @Override
            public void onError(Throwable e) {
                tv.append("onError :"+e.getMessage());
                tv.append("\n");
                Log.d(TAG," on Error:"+ e.getMessage());

            }

            @Override
            public void onComplete() {
                tv.append(" onComplete");
                tv.append("\n");
                Log.d(TAG,"onComplete");

            }
        }));


    }

    private Observable<String> sampleObservable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(2000);
                return Observable.just("one","two","three","four","five");
            }
        });
    }
}
