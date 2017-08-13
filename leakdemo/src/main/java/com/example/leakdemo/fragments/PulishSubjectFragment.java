package com.example.leakdemo.fragments;


import android.widget.TextView;

import com.example.leakdemo.R;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zhanghehe on 2017/8/13.
 */

public class PulishSubjectFragment extends Base2Fragment {

    @BindView(R.id.tv)
    TextView tv;
    @OnClick(R.id.btn)
    public void onBtnClick(){
        PublishSubject<Integer> source=PublishSubject.create();

        source.subscribe(getObserver("First"));

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        source.subscribe(getObserver("Second"));

        source.onNext(4);
        source.onComplete();
    }

    private Observer<? super Integer> getObserver(final String s) {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                addText("onSubcribe"+s+d.isDisposed());
            }

            @Override
            public void onNext(Integer integer) {
                addText("onNext"+s+integer);

            }

            @Override
            public void onError(Throwable e) {
                addText("onError"+s+e.getMessage());

            }

            @Override
            public void onComplete() {
                addText("onComplete"+s);

            }
        };
    }

    private void addText(String s) {
        tv.append(s);
        tv.append("\n");
    }
}
