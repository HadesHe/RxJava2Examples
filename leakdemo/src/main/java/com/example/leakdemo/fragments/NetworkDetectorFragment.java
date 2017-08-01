package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/1.
 */

public class NetworkDetectorFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private List<String> logs;
    private LogAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragmetn_network_detector;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private void setupLogger() {
        logs=new ArrayList<String>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }
}
