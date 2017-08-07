package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.model.ApiUser;
import com.example.leakdemo.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhanghehe on 2017/8/7.
 */

public class MapExampleFragnent extends Base2Fragment{

    private static final String TAG=MapExampleFragnent.class.getSimpleName();

    @BindView(R.id.tv)
    TextView tv;

    @OnClick(R.id.btn)
    void onClick(){
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<ApiUser>, List<User>>() {
                    @Override
                    public List<User> apply(@NonNull List<ApiUser> apiUsers) throws Exception {
                        return Utils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .subscribe(getObserver());

    }

    private Observable<List<ApiUser>> getObservable(){
        return Observable.create(new ObservableOnSubscribe<List<ApiUser>>() {
            @Override
            public void subscribe(ObservableEmitter<List<ApiUser>> e) throws Exception {

                if (!e.isDisposed()) {
                    e.onNext(Utils.getApiUserList());
                    e.onComplete();
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
                tv.append("onNext");
                tv.append("\n");

                for (User user : users) {
                    tv.append("firstname: "+ user.firstname);
                    tv.append("\n");
                }
                Log.d(TAG," onNext:"+ users.size());

            }

            @Override
            public void onError(Throwable e) {
                tv.append(" onError:"+e.getMessage());
                tv.append("\n");
                Log.d(TAG," onError:"+e.getMessage());

            }

            @Override
            public void onComplete() {
                tv.append("onComplete");
                tv.append("\n");
                Log.d(TAG," onComplete");

            }

        };

    }


}
