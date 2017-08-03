package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanghehe on 2017/8/2.
 */

public class SingleObserverFragment extends Base2Fragment{
    private static final String TAG = SingleObserverFragment.class.getSimpleName();
    @BindView(R.id.tv)
    TextView tv;


    @OnClick(R.id.btn)
    void onClick(){
        Single.just("Amit")
                .subscribe(getSingleObserver());
    }

    private SingleObserver<String> getSingleObserver() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG," onSubscribe :"+ d.isDisposed());
            }

            @Override
            public void onSuccess(String s) {
                tv.append(" onNext : value:"+ s);
                tv.append("\n");
                Log.d(TAG," on Next value"+ s);

            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError : value:"+ e.getMessage());
                tv.append("\n");
                Log.d(TAG," on Error value"+ e.getMessage());

            }
        };
    }
}
