package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/27.
 */

public class BufferFragmen extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    @BindView(R.id.btnStartOp)
    Button btnStartOp;
    @Override
    public int layoutResId() {
        return R.layout.fragment_buffer;
    }

    @Override
    public void onResume() {
        super.onResume();
//        disposable1=getBufferedDisposable();
    }

//    private Disposable getBufferedDisposable() {
//        return RxView.clicks(btnStartOp).map(new Function<On, Long>() {
//            @Override
//            public Long apply(@NonNull View.OnClickListener onClickListener) throws Exception {
//                return 1L;
//            }
//        });
//    }


}
