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

public class MergeExampleFragment extends Base2Fragment {
    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onClick(){

        final String[] aStrings={"A1","A2","A3","A4"};
        final String[] bStrings={"B1","B2","B3"};

        final Observable<String > aObservable=Observable.fromArray(aStrings);
        final Observable<String > bObservable=Observable.fromArray(bStrings);


        Observable.merge(aObservable,bObservable)
                .subscribe(getObserver());


    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                addTextToTv("onSubscribe:"+d.isDisposed());


            }

            @Override
            public void onNext(String s) {
                addTextToTv("onNext:"+ s);

            }

            @Override
            public void onError(Throwable e) {
                addTextToTv("onError "+e.getMessage());

            }

            @Override
            public void onComplete() {
                addTextToTv("onComplete");

            }
        };
    }

    private void addTextToTv(String s) {
        tv.append(s);
        tv.append("\n");
    }
}
