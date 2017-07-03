package com.example.leakdemo.base;

import android.support.v4.app.Fragment;

import com.example.leakdemo.MyApplication;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher= MyApplication.getRefWatcher();
        refWatcher.watch(this);
    }
}
