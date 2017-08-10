package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/10.
 */

public class SkipExampleFragment extends Base2Fragment {
    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onBtnClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skip(2)
                .subscribe(getObserver());
    }

    private Observable<Integer> getObservable() {
        return Observable.just(1,2,3,4,5,6,7,8);
    }

    private Observer<Integer> getObserver(){
        return new Observer<Integer>(){

            @Override
            public void onSubscribe(Disposable d) {
                tv.append("onSubscribe"+ d.isDisposed());
                tv.append("\n");

            }

            @Override
            public void onNext(Integer integer) {
                tv.append("onNext value:"+ integer);
                tv.append("\n");
            }


            @Override
            public void onError(Throwable e) {
                tv.append(" onError"+e.getMessage());
                tv.append("\n");

            }

            @Override
            public void onComplete() {
                tv.append("onComplete");
                tv.append("\n");

            }
        };
    }
}
