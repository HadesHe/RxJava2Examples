package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.model.ApiUser;
import com.example.leakdemo.utils.Utils;

import java.util.List;
import java.util.Observer;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

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

    private Observer<List<User>>  getObserver(){}


}
