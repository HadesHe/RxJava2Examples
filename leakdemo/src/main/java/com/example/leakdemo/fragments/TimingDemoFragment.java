package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DefaultSubscriber;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

import static android.os.Looper.getMainLooper;
import static android.os.Looper.myLooper;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/27.
 */

public class TimingDemoFragment extends BaseFragment {

    private static final String TAG = TimingDemoFragment.class.getSimpleName();
    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private List<String> logs;
    private LogAdapter adapter;
    private DisposableSubscriber subscrib1;
    private DisposableSubscriber<Long> subscrib2;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private void setupLogger() {
        logs=new ArrayList<>();
        adapter=new LogAdapter(getContext(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }

    @Override
    public int layoutResId() {
        return R.layout.fragment_timing;
    }

    @OnClick(R.id.btnTiming1)
    public void btnRunSingleTaskAfter2s(){
        log(String.format("A1 [%s] --- BTN click",getCurrentTimestamp()));

        Flowable.timer(2, TimeUnit.SECONDS)
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        log(String.format("A1 [%s] NEXT",getCurrentTimestamp()));
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t,"something went wrong in TimingDemoFragemt example");

                    }

                    @Override
                    public void onComplete() {
                        log(String.format("A1 [%s] XXX COMPLETE",getCurrentTimestamp()));

                    }
                });

    }

    @OnClick(R.id.btnTiming2)
    public void btnRunTaskIntervalOf1s(){
        if(subscrib1 != null&& !subscrib1.isDisposed()){
            subscrib1.dispose();
            log(String.format("B2 [%s] XXX BTN KILLED",getCurrentTimestamp()));
            return;
        }

        log(String.format("B2 [%s] ---- BTN click",getCurrentTimestamp()));

        subscrib1=new DisposableSubscriber<Long>(){

            @Override
            public void onNext(Long aLong) {
                log(String.format("B2 [%s] NEXT",getCurrentTimestamp()));

            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t,"something went wrong in TimingDemoFragment example");

            }

            @Override
            public void onComplete() {
                log(String.format("B2 [%s] COMPLETE",getCurrentTimestamp()));

            }
        };

        Flowable.interval(1,TimeUnit.SECONDS).subscribe(subscrib1);
    }

    @OnClick(R.id.btnTiming3)
    public void btnRunTaskIntervalOf1sStartImmediately(){
        if (subscrib2 != null&& !subscrib2.isDisposed()) {
            subscrib2.dispose();
            log(String.format("C3 [%s] XXX BTN KILLED",getCurrentTimestamp()));
            return;
        }
        log(String.format("C3 [%s] ---BTN CLICK",getCurrentTimestamp()));
        subscrib2=new DisposableSubscriber<Long>(){
            @Override
            public void onNext(Long aLong) {
                log(String.format("C3 [%s] NEXT",getCurrentTimestamp()));
            }

            @Override
            public void onError(Throwable t) {
               Timber.e(t,"something went wrong in TimingDemoFragment example");
            }

            @Override
            public void onComplete() {
                log(String.format("C3 [%s] XXX COMPLETe",getCurrentTimestamp()));

            }
        };
        Flowable.interval(0,1,TimeUnit.SECONDS).subscribe(subscrib2);

    }

    @OnClick(R.id.btnTiming4)
    public void btnRunTask5TimeIntervalOf3s(){
        log(String.format("D4 [%s] ----BTN click",getCurrentTimestamp()));

        Flowable.interval(3,TimeUnit.SECONDS)
                .take(5)
                .subscribe(new DefaultSubscriber<Long>() {
                    @Override
                    public void onNext(Long aLong) {
                        log(String.format("D4 [%s] NEXT",getCurrentTimestamp()));

                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t,TAG+" went wrong");

                    }

                    @Override
                    public void onComplete() {
                        log(String.format("D4 [%s] COMPLETE",getCurrentTimestamp()));

                    }
                });
    }

    @OnClick(R.id.btnTiming5)
    public void btnRunTask5TimesIntervalOf3s(){
        log(String.format("D5 [%s] ---- BTN click",getCurrentTimestamp()));

        Flowable.just("Do Task a right away")
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log(String.format("D5 %s [%s]",s,getCurrentTimestamp()));
                    }
                }).delay(1,TimeUnit.SECONDS)
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        log(String.format("D5 %s [%s]","Doing Task B after a delay",getCurrentTimestamp()));
                    }
                }).subscribe(new DefaultSubscriber<String>() {
            @Override
            public void onNext(String s) {
                log(String.format("D5 [%s] next",getCurrentTimestamp()));
            }

            @Override
            public void onError(Throwable t) {
                Timber.e(t,TAG+" went wrong");

            }

            @Override
            public void onComplete() {
                log(String.format("D5 [%s] NEXT",getCurrentTimestamp()));
            }
        });
    }

    @OnClick(R.id.btnTimingClr)
    public void onClearLog(){
        logs=new ArrayList<>();
        adapter.clear();
    }

    private void log(String logMsg){
        logs.add(0,String.format(logMsg+" [MainThread:%b]",getMainLooper()==myLooper()));

        new Handler(getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        adapter.addAll(logs);
                    }
                });
    }

    private String getCurrentTimestamp(){
        return new SimpleDateFormat("k:m:s:S a", Locale.getDefault()).format(new Date());
    }
}
