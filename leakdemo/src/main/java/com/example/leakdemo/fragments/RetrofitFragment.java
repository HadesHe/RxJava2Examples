package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.bean.Contributor;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.retrofit.GithubApi;
import com.example.leakdemo.retrofit.GithubService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static android.text.TextUtils.isEmpty;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/5.
 */

public class RetrofitFragment extends BaseFragment {

    @BindView(R.id.etDemoUsername)
    EditText etDemoUsername;

    @BindView(R.id.etDemoRepository)
    EditText etDemoRepository;

    @BindView(R.id.lstThreadingLog)
    ListView lstThreadingLog;
    private ArrayAdapter<String> adapter;
    private GithubApi mGithubService;

    @Override
    public int layoutResId() {
        return R.layout.fragment_retrofit;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter=new ArrayAdapter<String>(getContext(),R.layout.item_log,R.id.item_log,new ArrayList<String>());
        lstThreadingLog.setAdapter(adapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String githubToken=getString(R.string.github_oauth_token);
        mGithubService= GithubService.createGithubService(githubToken);

    }

    @OnClick(R.id.btnDemoContributors)
    public void onContributorsClick(){
        adapter.clear();

        disposable.add(
                mGithubService.contributors(etDemoUsername.getText().toString(),
                        etDemoRepository.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Contributor>>() {
                    @Override
                    public void onNext(List<Contributor> contributors) {
                        for (Contributor contributor : contributors) {
                            adapter.add(
                                    String.format(
                                            "%s has made %d contributions to %s",
                                            contributor.login,
                                            contributor.contributions,
                                            etDemoRepository.getText().toString()
                                    )
                            );

                            Timber.d(
                                    "%d has made %d contributions to %s",
                                    contributor.login,
                                    contributor.contributions,
                                    etDemoRepository.getText().toString()

                            );


                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e,"woops we got an error while getting the" +
                                " list of contirbutors");

                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Retrofit call 1 completed");

                    }
                }));

    }

    @OnClick(R.id.btnDemoUserInfo)
    public void onListContributorsWithFullUserInfoClicked(){
        adapter.clear();
        disposable.add(
                mGithubService.contributors(etDemoUsername.getText().toString(),
                        etDemoRepository.getText().toString())
                .flatMap(new Function<List<Contributor>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull List<Contributor> contributors) throws Exception {
                        return Observable.fromIterable(contributors);
                    }
                })
        )
    }

}
