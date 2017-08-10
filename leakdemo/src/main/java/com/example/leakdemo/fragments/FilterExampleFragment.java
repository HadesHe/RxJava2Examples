package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

/**
 * Created by zhanghehe on 2017/8/10.
 */

public class FilterExampleFragment extends Base2Fragment {
    private static final String TAG=FilterExampleFragment.class.getSimpleName();


    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onClick(){

        Observable.just(1,2,3,4,5,6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer%2==0;
                    }
                }).subscribe(getObserver());

    }

    private Observer<Integer> getObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                tv.append("onSubscribe "+d.isDisposed());
                tv.append("\n");
            }

            @Override
            public void onNext(Integer integer) {
                tv.append(" onNext:");
                tv.append("\n");
                tv.append(" value: "+integer);
                tv.append("\n");

            }

            @Override
            public void onError(Throwable e) {
                tv.append("onError"+e.getMessage());
                tv.append("\n");

            }

            @Override
            public void onComplete() {
                tv.append(" onComplete");
                tv.append("\n");

            }
        };
    }
}
