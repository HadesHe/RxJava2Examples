package com.example.leakdemo.pagination;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;

import com.example.leakdemo.MainActivity;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;

import butterknife.BindView;

/**
 * Created by zhanghehe on 2017/8/1.
 */

public class PaginationAutoFragment extends BaseFragment {

    @BindView(R.id.pbPagination)
    ProgressBar pbPagination;

    @BindView(R.id.rvPagination)
    RecyclerView rvPagination;

    @Override
    public int layoutResId() {
        return R.layout.fragment_pagination;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rxBus=((MainActivity)getActivity()).getRxBusSingleton();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPagination.setLayoutManager(layoutManager);

    }
}
