package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanghehe on 2017/8/10.
 */

public class FilterExampleFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onClick(){

    }
}
