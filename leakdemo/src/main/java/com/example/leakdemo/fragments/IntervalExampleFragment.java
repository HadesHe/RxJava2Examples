package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import java.util.Observer;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/9.
 */

public class IntervalExampleFragment extends Base2Fragment {

    private final String TAG=IntervalExampleFragment.class.getSimpleName();

    private final CompositeDisposable disposables=new CompositeDisposable();

    @BindView(R.id.tv)
    TextView tv;
    private DisposableObserver<Long> d;

    @OnClick(R.id.btn)
    public void onClick(){
        d=getObserver();
        getObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(d);

    }

    private Observable<Long> getObservable(){
        return Observable.interval(0,2, TimeUnit.SECONDS);
    }

    private DisposableObserver< Long> getObserver(){
        return new DisposableObserver<Long>() {



            @Override
            public void onNext(Long aLong) {
                if(aLong<10) {
                    tv.append("onNext :value" + aLong);
                    tv.append("\n");
                }else {
                    d.dispose();
                }

            }

            @Override
            public void onError(Throwable e) {
                tv.append("onError: "+e.getMessage());
                tv.append("\n");

            }

            @Override
            public void onComplete() {
                tv.append("onCompelte");
                tv.append("\n");

            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
