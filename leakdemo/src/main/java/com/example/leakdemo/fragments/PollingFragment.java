package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/7/16.
 */

public class PollingFragment extends BaseFragment {

    private static final int POLL_COUNT = 8;
    private static final int INITIAL_DELAY = 0;
    private static final int POLLING_INTERVAL = 1000;

    private ArrayList<String> logs;
    private LogAdapter adapter;

    @BindView(R.id.lstThreadingLog)
    ListView logsList;
    private int count;

    @Override
    public int layoutResId() {
        return R.layout.fragment_polling;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpLogger();
    }

    private void setUpLogger() {
        logs=new ArrayList<String>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        logsList.setAdapter(adapter);
        count=0;

    }

    @OnClick(R.id.btnSimplePolling)
    public void onSimplePollingClick(){
        final int pollCount=POLL_COUNT;

        Disposable d= Flowable.interval(INITIAL_DELAY,POLLING_INTERVAL, TimeUnit.MILLISECONDS)
                .map(new Function<Long, String>() {
                    @Override
                    public String apply(@NonNull Long aLong) throws Exception {
                        return doNetworkCallAndGetStringResult(aLong);
                    }
                })
                .take(pollCount)
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        log(String.format("Start simple polling -%s ",count));

                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                       log(String.format("Executing polled  [%s] now time : [xx;%02d]",s,getSecondHand()));

                    }
                });

        disposable.add(d);

    }

    @OnClick(R.id.btnIncreasinglyDelayedPolling)
    public void onIncreasinglyDelayedPolling(){
        setUpLogger();

        final  int pollingInterval=POLLING_INTERVAL;
        final int pollCount=POLL_COUNT;

        log(String.format("Start increasingly delayed polling now time: [xx:%02d]",getSecondHand()));


        Disposable d=Flowable.just(1L)
                .repeatWhen(new RepeatWithDelay(pollCount,pollingInterval))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        log(String.format("Executing polled task now time: [xx:%02d]", getSecondHand()));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Timber.d(throwable,"arrrr. Error");
                    }
                });

        disposable.add(d);


    }

    private int getSecondHand() {
        long mills=System.currentTimeMillis();
        return (int) (TimeUnit.MILLISECONDS.toSeconds(mills)
                -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mills)));
    }

    private void log(String logMsg){
        if(isCurrentlyOnMainThread()){
            logs.add(0,logMsg+"  (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        }else{
            logs.add(0,logMsg+"  (not main thread) ");
            new Handler(Looper.getMainLooper())
            .post(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    adapter.addAll(logs);
                }
            });

        }
    }

    private String doNetworkCallAndGetStringResult(long attempt){
        try{
            if (attempt==4) {
                Thread.sleep(9000);
            }else{
                Thread.sleep(3000);
            }
        }catch (InterruptedException e){
            Timber.d("Opration was interrupted");
        }
        count++;

        return String.valueOf(count);
    }

    public class RepeatWithDelay implements Function<Flowable<Object>,Publisher<Long>>{

        private final int repeatLimit;
        private final int pollingInterval;
        private int repeatCount=1;

        RepeatWithDelay(int repeatLimit,int pollingInterval){
            this.pollingInterval=pollingInterval;
            this.repeatLimit=repeatLimit;
        }

        @Override
        public Publisher<Long> apply(@NonNull Flowable<Object> objectFlowable) throws Exception {
            return objectFlowable.flatMap(new Function<Object, Publisher<Long>>() {
                @Override
                public Publisher<Long> apply(@NonNull Object o) throws Exception {
                    if(repeatCount>=repeatLimit){
                        log("Completing sequence");
                        return Flowable.empty();
                    }

                    repeatCount++;
                    return Flowable.timer(repeatCount*pollingInterval,TimeUnit.MILLISECONDS);
                }
            });
        }
    }
}
