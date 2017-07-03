package com.example.leakdemo.volley;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.wiring.LogAdapter;

import org.json.JSONObject;
import org.reactivestreams.Publisher;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/3.
 */

public class VolleyDemoFragment extends BaseFragment {

    private static final String TAG = VolleyDemoFragment.class.getSimpleName();
    private Unbinder unbinder;
    private List<String> logs;
    private LogAdapter adapter;

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadlingLog;

    private CompositeDisposable disposable=new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_volley,container,false);
        unbinder= ButterKnife.bind(this,layout);
        return layout;
    }

    @Override
    public void onPause() {
        super.onPause();
        disposable.clear();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupLogger();
    }

    private void setupLogger() {
        logs=new ArrayList<>();
        adapter=new LogAdapter(getActivity(),new ArrayList<String>());
        lstThreadlingLog.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnVolleyStart)
    void startRequest(){
        startVolleyRequest();
    }

    private void startVolleyRequest() {
        DisposableSubscriber<JSONObject> d=
                new DisposableSubscriber<JSONObject>() {
                    @Override
                    public void onNext(JSONObject jsonObject) {
                        Log.e(TAG,"onNext "+jsonObject.toString());
                        log("onNext "+ jsonObject.toString());

                    }

                    @Override
                    public void onError(Throwable t) {
                        VolleyError cause= (VolleyError) t.getCause();
                        String s=new String(cause.networkResponse.data, Charset.forName("UTF-8"));
                        Log.e(TAG,cause.toString());
                        log("onError "+s);

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG,"onComplete");
                        Timber.d("----- onCompleted");
                        log("onCompleted ");

                    }
                };
        newGetRouteData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(d);

        disposable.add(d);

    }

    public Flowable<JSONObject> newGetRouteData(){
        return Flowable.defer(new Callable<Publisher<? extends JSONObject>>() {
            @Override
            public Publisher<? extends JSONObject> call() throws Exception {
                return Flowable.just(getRouteData());
            }
        });


    }

    private JSONObject getRouteData() throws ExecutionException, InterruptedException {
        RequestFuture<JSONObject> future=RequestFuture.newFuture();
        String url = "http://www.weather.com.cn/adat/sk/101010100.html";
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET,url,future,future);
        MyVolley.getRequestQueue().add(req);
        return future.get();
    }

    private void log(String logMsg){
        if(isCurrentlyOnMainThread()){
            logs.add(0,logMsg+" (main thread) ");
            adapter.clear();
            adapter.addAll(logs);
        }else{
            logs.add(0,logMsg+ " (Not main thread) ");
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

    private boolean isCurrentlyOnMainThread(){
        return Looper.myLooper()==Looper.getMainLooper();
    }


}
