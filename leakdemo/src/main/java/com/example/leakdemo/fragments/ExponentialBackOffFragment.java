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
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import hu.akarnokd.rxjava2.math.MathFlowable;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
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
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        log("Attempting the impossible 5 times in intervals of 1s");
                    }
                }).subscribe(disposableSubscriber);
        disposable.add(disposableSubscriber);
    }

    @OnClick(R.id.btnExBackoffDelay)
    public void startExecutingWithExponentBackOffDelay(){
        logs=new ArrayList<>();
        adapter.clear();

        DisposableSubscriber<Integer> disposableSubscriber=
                new DisposableSubscriber<Integer>() {
                    @Override
                    public void onNext(Integer integer) {
                        Timber.d("executing Task %d [xx:%2d]",integer,getSecondHand());
                        log(String.format("executing Task %d [xx:%2d]",integer,getSecondHand()));

                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.d(t,"arrr.Error");
                        log("Error");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete");
                        log("Completed");

                    }
                };

                Flowable.range(1,4)
                        .delay(new Function<Integer, Publisher<? extends Object>>() {
                            @Override
                            public Publisher<? extends Object> apply(@NonNull final Integer integer) throws Exception {
                                return MathFlowable.sumInt(Flowable.range(1,integer))
                                        .flatMap(new Function<Integer, Publisher<?>>() {
                                            @Override
                                            public Publisher<?> apply(@NonNull Integer targetSencondDelay) throws Exception {
                                                return Flowable.just(integer).delay(targetSencondDelay,TimeUnit.SECONDS);
                                            }
                                        });
                            }
                        }).doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(@NonNull Subscription subscription) throws Exception {
                        log(String.format("Execute 4 tasks with delay - time now:[xx:%2d]",getSecondHand()));
                    }
                }).subscribe(disposableSubscriber);
        disposable.add(disposableSubscriber);
    }

    private int getSecondHand(){
        long millis=System.currentTimeMillis();
        return (int) (TimeUnit.MILLISECONDS.toSeconds(millis)
                        -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
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
