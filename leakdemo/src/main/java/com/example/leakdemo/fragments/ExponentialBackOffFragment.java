package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/7/18.
 */

public class ExponentialBackOffFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView logList;
    private ArrayList<String> logs;
    private LogAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragment_exponential_back;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private void setupLogger() {
        logs=new ArrayList<String>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        logList.setAdapter(adapter);

    }

    @OnClick(R.id.btnExBackoffRetry)
    public void startRetryWithExponentialBackoffStategy(){
        logs=new ArrayList<String>();

        adapter.clear();

        DisposableSubscriber<Object> disposableSubscriber=
                new DisposableSubscriber<Object>() {
                    @Override
                    public void onNext(Object o) {
                        Timber.d("onNext");

                    }

                    @Override
                    public void onError(Throwable t) {
                        log("onError : I give up!");

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete");

                    }
                };
        Flowable.error(new RuntimeException("testing"))
                .retryWhen(new RetryWithDelay(5,1000))
    }

    private void log(String msg){
        logs.add(msg);
        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(logs);
                    }
                });
    }

    public class RetryWithDelay implements Function<Flowable<? extends Throwable>,Publisher<?>>{

        private final int maxRetries;
        private final int retryDelayMills;
        private int retryCount;

        public RetryWithDelay(final int maxRetries, final int retryDelayMillis){
            this.maxRetries=maxRetries;
            this.retryDelayMills=retryDelayMillis;
            this.retryCount=0;

        }
        @Override
        public Publisher<?> apply(@NonNull Flowable<? extends Throwable> flowable) throws Exception {
            return flowable.flatMap(new Function<Throwable, Publisher<?>>() {
                @Override
                public Publisher<?> apply(@NonNull Throwable throwable) throws Exception {
                    if(++retryCount<maxRetries){
                        Timber.d("Retrying in %d ms",retryCount*retryDelayMills);
                        log(String.format("Retrying in %d ms",retryCount*retryDelayMills));
                        return Flowable.timer(retryCount*retryDelayMills, TimeUnit.MILLISECONDS);
                    }

                    Timber.d("Argh! i give up");
                    return Flowable.error(throwable);
                }
            });
        }
    }
}
