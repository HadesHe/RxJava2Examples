package com.example.leakdemo.rxbus;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leakdemo.MainActivity;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.fragments.RxBusFragment;

import butterknife.OnClick;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/28.
 */

public class RxBusTopFragment extends BaseFragment {
    private RxBus rxBus;

    @Override
    public int layoutResId() {
        return R.layout.fragment_rxbus_top;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rxBus=((MainActivity)getActivity()).getRxBusSingleton();

    }

    @OnClick(R.id.btnRxbusTap)
    public void onTapButton(){
        if(rxBus.hasObservers()){
            rxBus.send(new RxBusFragment.TapEvent());
        }
    }
}
