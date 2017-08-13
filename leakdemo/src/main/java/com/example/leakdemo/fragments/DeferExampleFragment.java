package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.model.Car;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class DeferExampleFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onBtnClick(){
        Car car=new Car();

        Observable<String> brandDeferObservable=car.brandDeferObservable();
        car.setBrand("BMW");

        brandDeferObservable.subscribe(
                getObserver()
        );


    }

    private Observer<String> getObserver(){
        return new Observer<String>(){
            @Override
            public void onSubscribe(Disposable d) {
                addTextToTv("onSubscribe:"+d.isDisposed());

            }

            @Override
            public void onNext(String s) {
                addTextToTv("onNext :"+s);

            }

            @Override
            public void onError(Throwable e) {
                addTextToTv("onError :"+e.getMessage());

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
