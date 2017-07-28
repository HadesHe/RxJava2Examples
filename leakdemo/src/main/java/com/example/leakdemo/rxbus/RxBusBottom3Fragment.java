package com.example.leakdemo.rxbus;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leakdemo.MainActivity;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.fragments.RxBusFragment;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.flowables.ConnectableFlowable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/28.
 */

public class RxBusBottom3Fragment extends BaseFragment {


    private RxBus rxBus;

    @Override
    public int layoutResId() {
        return R.layout.fragment_rxbus_bottom3;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rxBus=((MainActivity)getActivity()).getRxBusSingleton();
    }

    @Override
    public void onStart() {
        super.onStart();

        ConnectableFlowable<Object> tapEventEmiteter=rxBus.asFlowable().publish();

        disposable.add(tapEventEmiteter.subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if(o instanceof RxBusFragment.TapEvent){
                    showTapText();
                }
            }
        }));
       tapEventEmiteter.publish()
    }
}
