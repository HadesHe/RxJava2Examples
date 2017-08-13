package com.example.leakdemo.fragments;

import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public abstract class Base3Fragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onBtnClick(){
        doSomeClick();
    }

    public abstract void doSomeClick() ;



    public void addText(String s) {
        tv.append(s);
        tv.append("\n");
    }
}
