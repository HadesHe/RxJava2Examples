package com.example.leakdemo.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public class BufferDemoFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    @BindView(R.id.btnStartOp)
    Button btnStartOp;
    Disposable mDisposable;

    @Override
    public int layoutResId() {
        return R.layout.fragment_volley;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDisposable=getBufferedDisposable();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private Disposable getBufferedDisposable(){
         RxView.clicks(btnStartOp)
                .map(new Function<View.OnClickListener, Integer>() {
                    @Override
                    public Integer apply(@NonNull View.OnClickListener onClickListener) throws Exception {
                        return 1;
                    }
                });


    }
}
