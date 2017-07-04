package com.example.leakdemo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jakewharton.rxbinding2.widget.TextViewTextChangeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import hu.akarnokd.rxjava2.async.DisposableObservable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import timber.log.Timber;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/4.
 */

public class DebounceSearchEmitterFrament extends BaseFragment {

    @BindView(R.id.etDebounce)
    EditText etDebounce;

    @BindView(R.id.ivClear)
    ImageView ivClear;

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private List<String> logs;
    private DisposableObserver<TextViewTextChangeEvent> mDisposable;
    private LogAdapter adapter;

    @Override
    public int layoutResId() {
        return R.layout.fragment_debounce;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();

        mDisposable = RxTextView.textChangeEvents(etDebounce)
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(new Predicate<TextViewTextChangeEvent>() {
                    @Override
                    public boolean test(@io.reactivex.annotations.NonNull TextViewTextChangeEvent textViewTextChangeEvent) throws Exception {
                        return TextUtils.isEmpty(textViewTextChangeEvent.text().toString());
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getSearchObserver());
    }

    private void setupLogger() {
        logs=new ArrayList<>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);
    }

    private DisposableObserver<TextViewTextChangeEvent> getSearchObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onNext(TextViewTextChangeEvent textViewTextChangeEvent) {
                log(String.format("Search for %s",textViewTextChangeEvent.text().toString()));
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e,"------------Woops on error");
                log("Dang error.Check your logs");

            }

            @Override
            public void onComplete() {
                Timber.d("---------------onComplete");

            }
        };
    }

    private void log(String logMsg) {

        if (isCurrentlyOnMainThread()) {
            logs.add(0, logMsg + " (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        } else {
            logs.add(0, logMsg + " (NOT main thread) ");

            // You can only do below stuff on main thread.
            new Handler(Looper.getMainLooper())
                    .post(new Runnable() {
                              @Override
                              public void run() {
                                  adapter.clear();
                                  adapter.addAll(logs);
                              }
                          }

                    );
        }
    }


    @OnClick(R.id.ivClear)
    public void onClear() {
        logs = new ArrayList<String>();
        adapter.clear();
    }


}
