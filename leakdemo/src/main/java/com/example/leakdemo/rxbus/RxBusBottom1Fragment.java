package com.example.leakdemo.rxbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.example.leakdemo.MainActivity;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.fragments.RxBusFragment;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by zhanghehe on 2017/7/29.
 */

public class RxBusBottom1Fragment extends BaseFragment {
    private RxBus rxBus;

    @BindView(R.id.tvRxbusTapCount)
    TextView tvRxbusTapCount;

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

        Disposable d=rxBus.asFlowable().subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if(o instanceof RxBusFragment.TapEvent){
                    showTapText();
                }
            }
        });
        disposable.add(d);
    }

    private void showTapText(){
        tvRxbusTapCount.setVisibility(View.VISIBLE);
        tvRxbusTapCount.setAlpha(1f);

    }
}
