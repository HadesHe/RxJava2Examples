package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.volley.VolleyDemoFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public class MainFragment extends BaseFragment {


    @Override
    public int layoutResId() {
        return R.layout.fragment_main;
    }



    @OnClick(R.id.btnDemoSchedulers)
    void concurrencyWithSchedulers(){
        clickedOn(new ConcurrencyWithSchedulersDemoFragment());
    }

    @OnClick(R.id.btnDemoVolley)
    void volleyRequestDemo(){
        clickedOn(new VolleyDemoFragment());
    }

    @OnClick(R.id.btnDemoBuffer)
    void bufferDemo(){
    }


    @OnClick(R.id.btnDemoDebounce)
    void debounceDemo(){
        clickedOn(new DebounceSearchEmitterFrament());
    }
    private void clickedOn(Fragment fragment){
        final String tag=fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content,fragment,tag)
                .commit();
    }

}
