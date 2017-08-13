package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class DistinctExampleFragment extends Base2Fragment{


    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onBtnClick(){
        getObservable()
                .distinct()
                .subscribe(getObserver());
    }

    private Observer<? super Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addText("onSubscribe"+d.isDisposed());

            }

            @Override
            public void onNext(Integer integer) {
                addText("onNext:"+integer);

            }

            @Override
            public void onError(Throwable e) {
                addText("onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                addText("onComplete");

            }
        };
    }

    private void addText(String s) {
        tv.append(s);
        tv.append("\n");
    }

    private Observable<Integer> getObservable() {
        return Observable.just(1,2,1,1,2,3,4,5,6);

    }


}
