package com.example.leakdemo.network;


import android.util.Log;

import com.example.leakdemo.R;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.fragments.Base2Fragment;
import com.example.leakdemo.model.ApiUser;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class NetworkFragment extends Base2Fragment {

    @Override
    protected int layoutResId() {
        return R.layout.fragment_network;
    }

    @OnClick(R.id.btnNetworkMap)
    public void onMapClick(){
        Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUser/{userId}")
                .addPathParameter("userId","1")
                .build()
                .getObjectObservable(ApiUser.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ApiUser, User>() {
                    @Override
                    public User apply(@NonNull ApiUser apiUser) throws Exception {
                        User user=new User(apiUser);
                        return user;
                    }
                }).subscribe(getObserver("map"));


    }

    private Observer<? super User> getObserver(final String s) {
        return new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(s,"onSubscribe"+d.isDisposed());
            }

            @Override
            public void onNext(User user) {
                Log.d(s,"onNext"+user.toString());

            }

            @Override
            public void onError(Throwable e) {
                Log.d(s,"onError"+e.getMessage());

            }

            @Override
            public void onComplete() {
                Log.d(s,"onComplete");

            }
        };
    }

    @OnClick(R.id.btnNetworkZip)
    public void onZipClick(){
        Observable.zip(getCircketFansObservable(), getFootballFabsObservable(), new BiFunction<List<User>, List<User>, List<User>>() {
            @Override
            public List<User> apply(@NonNull List<User> userList, @NonNull List<User> userList2) throws Exception {
                return null;
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<User> users) {
                        Log.d("Zip","users .size"+users.size());
                        for (User user : users) {
                            Log.d("Zip","user: "+user.toString());
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Zip","onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("Zip","onComplete ");

                    }
                });

    }

    @OnClick(R.id.btnNetworkFlatMapAndFilter)
    public void onFlatMapAndFilterClick(){

    }

    @OnClick(R.id.btnNetworkTake)
    public void onTakeClick(){

    }

    @OnClick(R.id.btnNetworkFlatMap)
    public void onFlatMapClick(){

    }

    @OnClick(R.id.btnNetworkFlatMapWithZip)
    public void onFlatMapWithZip(){

    }


}
