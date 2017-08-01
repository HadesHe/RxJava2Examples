package com.example.leakdemo.pagination;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.leakdemo.MainActivity;
import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.rxbus.RxBus;

import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.processors.PublishProcessor;

/**
 * Created by zhanghehe on 2017/8/1.
 */

public class PaginationAutoFragment extends BaseFragment {

    @BindView(R.id.pbPagination)
    ProgressBar pbPagination;

    @BindView(R.id.rvPagination)
    RecyclerView rvPagination;
    private RxBus rxBus;
    private PaginationAutoAdapter adapter;
    private PublishProcessor<Integer> paginator;
    private boolean requestUnderWay=false;

    @Override
    public int layoutResId() {
        return R.layout.fragment_pagination;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rxBus=((MainActivity)getActivity()).getRxBusSingleton();

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPagination.setLayoutManager(layoutManager);

        adapter=new PaginationAutoAdapter(rxBus);

        paginator= PublishProcessor.create();
        Disposable d2=paginator.onBackpressureDrop()
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        requestUnderWay=true;
                        pbPagination.setVisibility(View.VISIBLE);
                    }
                }).concatMap(new Function<Integer, Flowable<List<String>>>() {
                    @Override
                    public Flowable<List<String>> apply(@NonNull Integer integer) throws Exception {
                        return itemsFromNetworkCall(integer);
                    }
                }).map(new Function<List<String>, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull List<String> strings) throws Exception {
                        adapter.addItems(strings);
                        return strings;
                    }
                }).doOnNext(new Consumer<List<String>>() {
                    @Override
                    public void accept(@NonNull List<String> strings) throws Exception {
                        requestUnderWay=false;
//                        pbPagination.setVisibility(View.INVISIBLE);
                    }
                }).subscribe();

        Disposable d1=rxBus.asFlowable().filter(new Predicate<Object>() {
            @Override
            public boolean test(@NonNull Object o) throws Exception {
                return !requestUnderWay;
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o) throws Exception {
                if(o instanceof PaginationAutoAdapter.PageEvent){
                    int nextPage=adapter.getItemCount();
                    paginator.onNext(nextPage);
                }
            }
        });

        disposable.add(d1);
        disposable.add(d2);

        paginator.onNext(0);
    }


    private Flowable<List<String>>  itemsFromNetworkCall(final int pageStart){
        return Flowable.just(true)
                .observeOn(AndroidSchedulers.mainThread())
                .delay(2, TimeUnit.SECONDS)
                .map(new Function<Boolean, List<String>>() {
                    @Override
                    public List<String> apply(@NonNull Boolean aBoolean) throws Exception {
                        List<String> list=new ArrayList<String>();

                        for (int i = 0; i < 10; i++) {
                            list.add("Item "+ (pageStart+i));
                        }
                        return list;
                    }
                });

    }
}
