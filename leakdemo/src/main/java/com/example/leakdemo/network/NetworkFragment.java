package com.example.leakdemo.network;


import com.example.leakdemo.R;
import com.example.leakdemo.fragments.Base2Fragment;

import butterknife.OnClick;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class NetworkFragment extends Base2Fragment {

    @Override
    protected int layoutResId() {
        return R.layout.fragment_network;
    }

    @OnClick(R.id.btnNetworkMap)
    public void onMapClick(){
        Rx2Android

    }

    @OnClick(R.id.btnNetworkZip)
    public void onZipClick(){

    }

    @OnClick(R.id.btnNetworkFlatMapAndFilter)
    public void onFlatMapAndFilterClick(){

    }

    @OnClick(R.id.btnNetworkTake)
    public void onTakeClick(){

    }

    @OnClick(R.id.btnNetworkFlatMap)
    public void onFlatMapClick(){

    }

    @OnClick(R.id.btnNetworkFlatMapWithZip)
    public void onFlatMapWithZip(){

    }


}
