package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;


import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/10.
 */

public class ScanExampleFragment extends Base2Fragment {
    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onBtnClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                        addEnterToTv("interger"+integer+",integer2"+integer2);
                        return integer+integer2;
                    }
                })
                .subscribe(getObserver());

    }

    private Observable<Integer> getObservable() {
        return Observable.just(1,2,3,4,5,6,7,8,9,10);
    }

    private Observer<Integer> getObserver(){
        return new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {
                addEnterToTv("onSubscribe:"+d.isDisposed());

            }

            @Override
            public void onNext(Integer integer) {
                addEnterToTv("onNext:"+integer);

            }

            @Override
            public void onError(Throwable e) {
                addEnterToTv("onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addEnterToTv("onComplete");

            }
        };
    }

    private void addEnterToTv(String msg){
        tv.append(msg);
        tv.append("\n");
    }
}
