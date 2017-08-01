package com.example.leakdemo.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.processors.PublishProcessor;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/8/1.
 */

public class NetworkDetectorFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private List<String> logs;
    private LogAdapter adapter;
    private PublishProcessor<Boolean> publishProcessor;
    private Disposable disposable1;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public int layoutResId() {
        return R.layout.fragmetn_network_detector;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    @Override
    public void onStart() {
        super.onStart();

        publishProcessor= PublishProcessor.create();

        disposable1=publishProcessor.startWith(getConnectivityStatus(getActivity()))
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            log("You are online");

                        }else{
                            log("you are offline");

                        }
                    }
                });

        listenToNetworkConnectivity();

    }

    private void listenToNetworkConnectivity() {
        broadcastReceiver=new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                publishProcessor.onNext(getConnectivityStatus(context));

            }
        };

        final IntentFilter intentFilter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        disposable1.dispose();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private void log(String logMsg) {

        if (isCurrentlyOnMainThread()) {
            logs.add(0, logMsg + " (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        } else {
            logs.add(0, logMsg + " (NOT main thread) ");

            // You can only do below stuff on main thread.
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    adapter.addAll(logs);
                }
            });
        }
    }

    private boolean getConnectivityStatus(Context context) {
        ConnectivityManager cm= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=cm.getActiveNetworkInfo();
        return networkInfo != null&&networkInfo.isConnected();

    }

    private void setupLogger() {
        logs=new ArrayList<String>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }
}
