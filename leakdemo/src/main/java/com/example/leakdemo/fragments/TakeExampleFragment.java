package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/8.
 */

public class TakeExampleFragment extends Base2Fragment{
    private static final String TAG=TakeExampleFragment.class.getSimpleName();

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onBtnClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(7)
                .subscribe(getObserver());


    }

    private Observer<Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG," onSUbscribe:"+ d.isDisposed());

            }

            @Override
            public void onNext(Integer integer) {
                tv.append(" onNext: value: "+integer);
                tv.append("\n");


            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError:" + e.getMessage());
                tv.append("\n");


            }

            @Override
            public void onComplete() {
                tv.append(" onComplete");
                tv.append("\n");

            }
        };
    }

    private Observable<Integer> getObservable(){
        return Observable.just(1,2,3,4,5);
    }
}
