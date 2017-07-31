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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/7/31.
 */

public class TimingOutFragment extends BaseFragment {

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private ArrayList<String> logs;
    private LogAdapter adapter;
    private DisposableObserver<String> disposable1;

    @Override
    public int layoutResId() {
        return R.layout.fragment_subject_timeout;
    }

    @OnClick(R.id.btnDemoTimeout2s)
    public void onStart2sTask(){
        disposable1=getEventCompletionObserver();
        getObservableTask2sToComplete()
                .timeout(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable1);

    }

    @OnClick(R.id.btnDemoTimeout5s)
    public void onStart5sTask(){
        disposable1=getEventCompletionObserver();

        getObservableTask5sToComplete()
                .timeout(3,TimeUnit.SECONDS,onTimeoutObservable())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable1);


    }

    private ObservableSource<String> onTimeoutObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                log("Timing out this task ...");
                e.onError(new Throwable("Timeout Error"));
            }
        });
    }

    private Observable<String> getObservableTask5sToComplete(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                log(String.format("Starting a 5s task"));
                e.onNext(" 5 s");
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                e.onComplete();
            }
        });
    }

    private DisposableObserver<String> getEventCompletionObserver() {
        return new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                log(String.format("onNext %s task",s));
            }

            @Override
            public void onError(Throwable e) {
                log(String.format("Dang a task timeout"));
                Timber.e(e,"Time out Demo Exception");

            }

            @Override
            public void onComplete() {
                log(String.format("task was completed"));

            }
        };
    }

    private Observable<String> getObservableTask2sToComplete(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                log(String.format("Starting a 2s task"));
                e.onNext("2s");
                try {
                    Thread.sleep(2000);
                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                e.onComplete();
            }
        });
    }

    private void log(final String logMsg){
        if(isCurrentlyOnMainThread()){
            logs.add(0,logMsg+" (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        }else{
            logs.add(0,logMsg+" (not main thread) ");

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    adapter.clear();
                    adapter.addAll(logs);

                }
            });
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private void setupLogger() {
        logs=new ArrayList<>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }
}
