package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leakdemo.MyApplication;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zhanghehe on 2017/8/1.
 */

abstract class Base2Fragment extends Fragment{
    private Unbinder unbinder;

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher= MyApplication.getRefWatcher();
        refWatcher.watch(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View layout=inflater.inflate(layoutResId(),container,false);
        unbinder= ButterKnife.bind(this,layout);
        return layout;
    }

    protected abstract int layoutResId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
