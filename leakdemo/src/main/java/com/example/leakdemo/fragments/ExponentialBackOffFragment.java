package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by zhanghehe on 2017/7/18.
 */

public class ExponentialBackOffFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView logList;
    @Override
    public int layoutResId() {
        return R.layout.fragment_exponential_back;
    }
}
