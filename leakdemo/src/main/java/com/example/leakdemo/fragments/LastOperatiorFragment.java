package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class LastOperatiorFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onBtnClick(){
        getObservable().last("A1")
                .subscribe(getObserver());
    }

    private SingleObserver<String> getObserver(){
        return new SingleObserver<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                addText("onSUbscribe"+d.isDisposed());

            }

            @Override
            public void onSuccess(String s) {
                addText("onSuccess"+s);

            }

            @Override
            public void onError(Throwable e) {
                addText("onError"+e.getMessage());

            }
        };
    }

    private void addText(String s) {
        tv.append(s);
        tv.append("\n");
    }

    private Observable<String> getObservable() {
        return Observable.just("A1","A2","A3","A4","A5","A6");
    }
}
