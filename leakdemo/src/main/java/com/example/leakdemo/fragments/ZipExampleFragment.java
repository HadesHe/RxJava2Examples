package com.example.leakdemo.fragments;


import android.util.Log;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/7.
 */

public class ZipExampleFragment extends Base2Fragment {

    private static final String TAG = "ZipExampleFragmet";
    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    public void onClick(){

        Observable.zip(getCricketFansObservable(), getFootballFansObservable()
                , new BiFunction<List<User>, List<User>, List<User>>() {
                    @Override
                    public List<User> apply(@NonNull List<User> users, @NonNull List<User> users2) throws Exception {
                        return Utils.filterUserWhoLovesBoth(users,users2);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());

    }

    private Observable<List<User>> getCricketFansObservable(){
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {
                if(!e.isDisposed()){
                    e.onNext(Utils.getUserListWhoLovesCricket());
                    e.onComplete();
                }
            }
        });
    }

    private Observable<List<User>> getFootballFansObservable(){
        return Observable.create(new ObservableOnSubscribe<List<User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<User>> e) throws Exception {

                if(!e.isDisposed()){
                    e.onNext(Utils.getUserListWhoLovesFootball());
                }
            }
        });
    }

    private Observer<List<User>> getObserver(){
        return new Observer<List<User>>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG," onSubscribe:"+ d.isDisposed());

            }

            @Override
            public void onNext(List<User> users) {
                tv.append("on Next");
                tv.append("\n");

                for (User user : users) {
                    tv.append(" firstname:"+user.firstname);
                    tv.append("\n");
                }

                Log.d(TAG," onNext:"+ users.size());

            }

            @Override
            public void onError(Throwable e) {

                tv.append(" onError "+ e.getMessage());
                tv.append("\n");

            }

            @Override
            public void onComplete() {
                tv.append("onComplete");
                tv.append("\n");

            }
        };

    }

}
