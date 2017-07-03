package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/3.
 */

public class ConcurrencyWithSchedulersDemoFragment extends BaseFragment {

    @BindView(R.id.pbOperationRunning)
    ProgressBar pbOperationRunning;

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private LogAdapter adapter;
    private List<String> logs;


    @Override
    public int layoutResId() {
        return R.layout.fragment_concurrency_shedulers;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    @OnClick(R.id.btnStartOp)
    public void startLongOperation(){
        setPbVisibility(View.VISIBLE);
        log("Button Clicked");

        DisposableObserver<Boolean> d = getDisposableObserver();
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d);
        disposable.add(d);

    }

    private Observable<Boolean> getObservable() {
        return Observable.just(true)
                .map(new Function<Boolean, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull Boolean aBoolean) throws Exception {
                        log("Within Observable");
                        blockCurrentThread();
                        return aBoolean;
                    }
                });
    }

    private DisposableObserver<Boolean> getDisposableObserver() {
        return new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {
                log(String.format("onNext with return value \"%b\"",aBoolean));
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e,"Error inRxJav Demo Concurrency");
                log(String.format("Boo! Error %s",e.getMessage()));
                setPbVisibility(View.INVISIBLE);

            }

            @Override
            public void onComplete() {
                log("On complete");
                setPbVisibility(View.INVISIBLE);

            }
        };
    }


    private void blockCurrentThread() {
        log("block current thread");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Timber.d("Operation was inter");
        }
    }

    private void setPbVisibility(int visible) {
        pbOperationRunning.setVisibility(visible);
    }

    private void setupLogger() {
        logs=new ArrayList<>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }

    private void log(String logMsg){
        if(isCurrentlyOnMainThread()){
            logs.add(0,logMsg+" (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        }else{
            logs.add(0,logMsg+" (not main thread) ");
            new Handler(Looper.getMainLooper()).
                    post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.clear();
                            adapter.addAll(logs);
                        }
                    });

        }
    }
}
