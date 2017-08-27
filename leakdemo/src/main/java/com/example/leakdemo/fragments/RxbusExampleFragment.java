package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.leakdemo.MyApplication;
import com.example.leakdemo.R;
import com.example.leakdemo.model.Events;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/16.
 */

public class RxbusExampleFragment extends Base2Fragment{

    @BindView(R.id.tvRxBus)
    TextView tvRxBus;

    public static final String TAG=RxbusExampleFragment.class.getSimpleName();

    private final CompositeDisposable disposable=new CompositeDisposable();


    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        disposable.add(((MyApplication) getActivity().getApplication()).bus()
                .toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        if(o instanceof Events.AutoEvent){
                            tvRxBus.setText("Auto Event Received");
                        }else if(o instanceof Events.TapEvent){
                            tvRxBus.setText("Tap Event Received");
                        }

                    }
                }));
    }

    @OnClick(R.id.btnRxBus)
    public void onClick(){
        ((MyApplication) getActivity().getApplication())
                .bus().send(new Events.TapEvent());
    }

    @Override
    protected int layoutResId() {
        return R.layout.fragment_rxbus_example;
    }
}
