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
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/3.
 */

public class SimpleExampleFragment extends Base2Fragment {

    private static final String TAG=SimpleExampleFragment.class.getSimpleName();

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    void onClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    private Observable<String> getObservable(){
        return Observable.just("Cricket","Football");
    }

    private Observer<String>  getObserver(){
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG," onSubscribe :"+d.isDisposed());
            }

            @Override
            public void onNext(String s) {
                appendText(" onNext : value : "+s);
            }

            @Override
            public void onError(Throwable e) {
                appendText(" onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                appendText(" onComplete ");

            }
        };
    }

    private void appendText(String s) {
        tv.append(s);
        tv.append("\n");
        Log.d(TAG,s);
    }
}
