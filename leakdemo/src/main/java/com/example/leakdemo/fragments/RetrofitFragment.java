package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/5.
 */

public class RetrofitFragment extends BaseFragment {

    @BindView(R.id.etDemoUsername)
    EditText etDemoUsername;

    @BindView(R.id.etDemoRepository)
    EditText etDemoRepository;

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private ArrayAdapter<String> adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragment_retrofit;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter=new ArrayAdapter<String>(getContext(),R.layout.item_log,R.id.item_log,new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);



    }
}
