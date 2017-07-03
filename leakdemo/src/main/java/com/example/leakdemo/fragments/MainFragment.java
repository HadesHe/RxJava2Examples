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

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_main,container,false);
        unbinder=ButterKnife.bind(this,layout);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnDemoVolley)
    void volleyRequestDemo(){
        clickedOn(new VolleyDemoFragment());
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
