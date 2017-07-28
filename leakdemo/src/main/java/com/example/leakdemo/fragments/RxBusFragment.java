package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.rxbus.RxBusBottom3Fragment;
import com.example.leakdemo.rxbus.RxBusTopFragment;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/27.
 */

public class RxBusFragment extends BaseFragment {
    @Override
    public int layoutResId() {
        return R.layout.fragment_rxbus;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: 2017/7/28 switch different bottom fragment
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.flRxbusFrag1, new RxBusTopFragment())
                .replace(R.id.flRxbusFrag2, new RxBusBottom3Fragment())
//        .replace(R.id.flRxbusFrag2,new RxBusBottom2Fragment())
//        .replace(R.id.flRxbusFrag2,new RxBusBottom1Fragment())
        .commit();

    }

    public static class TapEvent {
    }
}
