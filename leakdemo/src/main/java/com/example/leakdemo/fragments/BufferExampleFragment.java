package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanghehe on 2017/8/9.
 */

public class BufferExampleFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onClick(){
        Observable<List<String>> buffered=getObservable().buffer(3,1);

        buffered.subscribe(getObserver());

    }

    private Observable<String> getObservable(){
        return Observable.just("one","two","three","four","five");
    }

    private Observer<List<String>> getObserver(){
        return new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {
                tv.append("onSubscribe"+d.isDisposed());
                tv.append("\n");

            }

            @Override
            public void onNext(List<String> strings) {
                tv.append("onNext size"+strings.size());
                tv.append("\n");

                for (String string : strings) {
                    tv.append(string);
                    tv.append("\n");
                }

            }

            @Override
            public void onError(Throwable e) {
                tv.append("onError"+e.getMessage());
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
