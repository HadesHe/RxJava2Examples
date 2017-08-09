package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanghehe on 2017/8/9.
 */

public class IntervalExampleFragment extends Base2Fragment {

    private final String TAG=IntervalExampleFragment.class.getSimpleName();

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onClick(){

    }
}
