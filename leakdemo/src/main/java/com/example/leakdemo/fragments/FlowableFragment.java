package com.example.leakdemo.fragments;


import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.SingleObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.internal.operators.single.SingleObserveOn;

/**
 * Created by zhanghehe on 2017/8/1.
 */

public class FlowableFragment extends Base2Fragment {

    private static final String TAG = FlowableFragment.class.getSimpleName();
    @BindView(R.id.tv)
    TextView tv;
    @Override
    protected int layoutResId() {
        return R.layout.fragment_example;
    }

    @OnClick(R.id.btn)
    public void onClick(){
        Flowable<Integer> observable=Flowable.just(1,2,3,4);

        observable.reduce(50, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(@NonNull Integer integer, @NonNull Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).subscribe(getObserver());


    }

    private SingleObserver<Integer> getObserver() {

        return new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe:"+ d.isDisposed());

            }

            @Override
            public void onSuccess(Integer integer) {
                tv.append(" onSuccess: value :"+ integer);
                tv.append("\n");
                Log.d(TAG,"on Success: value: "+integer);

            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError:" +e.getMessage());
                tv.append("\n");
                Log.d(TAG," onError :"+ e.getMessage());

            }
        };
    }
}
