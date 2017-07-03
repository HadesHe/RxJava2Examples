package com.example.leakdemo.base;

import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leakdemo.MyApplication;
import com.squareup.leakcanary.RefWatcher;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public abstract class BaseFragment extends Fragment {

    protected CompositeDisposable disposable=new CompositeDisposable();
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
        unbinder=ButterKnife.bind(this,layout);
        return layout;
    }

    public abstract int layoutResId();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
    }

    protected boolean isCurrentlyOnMainThread(){
        return Looper.myLooper()==Looper.getMainLooper();
    }
}
