package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/2.
 */

public class DisposableExample extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @Override
    public int layoutResId() {
        return R.layout.fragment_example;
    }

    @OnClick(R.id.btn)
    public void onBtnClick(){
        disposables.add()

    }
}
