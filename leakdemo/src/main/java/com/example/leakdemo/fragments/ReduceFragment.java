package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;

/**
 * Created by zhanghehe on 2017/8/9.
 */

public class ReduceFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onBtn(){
        getObservable()
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        return integer+integer2;
                    }
                })
                .subscribe(getObserver());
    }

    private Observable<Integer> getObservable() {
        return Observable.just(1,2,3,4,5);
    }

    private MaybeObserver<Integer> getObserver(){
        return new MaybeObserver<Integer>(){
            @Override
            public void onSubscribe(Disposable d) {
                tv.append("onSubscribe"+d.isDisposed());
                tv.append("\n");

            }

            @Override
            public void onSuccess(Integer integer) {
                tv.append("onSuccess value: "+integer);
                tv.append("\n");


            }

            @Override
            public void onError(Throwable e) {
                tv.append("onError: "+e.getMessage());
                tv.append("\n");

            }

            @Override
            public void onComplete() {
                tv.append("onComplete: ");
                tv.append("\n");

            }
        };
    }

}
