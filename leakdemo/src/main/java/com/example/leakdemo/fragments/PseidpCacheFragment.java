package com.example.leakdemo.fragments;

import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.bean.Contributor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.internal.operators.observable.ObservableCache;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by zhanghehe on 2017/7/22.
 */

public class PseidpCacheFragment extends BaseFragment {

    @BindView(R.id.tvPseudoCacheInfo)
    TextView tvPseudoCacheInfo;

    @BindView(R.id.lstPseudoCacheListSubscription)
    ListView lstPseudoCacheListSubscription;

    @BindView(R.id.lstPseudoCacheDetail)
    ListView lstPseudoCacheDetail;
    private HashMap<String, Long> contributionMap=null;
    private ArrayAdapter<String> adapterDetail;
    private ArrayAdapter<String> adapterSubscriptionInfo;

    @Override
    public int layoutResId() {
        return R.layout.fragment_pseudo_cache;
    }

    /**
     * concat: the latter observable doesn't start until the former Observable completes
     */
   @OnClick(R.id.btnPseudoCacheContat)
    public void onContatBtnClick(){
       setPseudoCacheInfo("Contat");
       wireupDemo();

       Observable.concat(getSlowCacheDiskData(),getFreshNetworkData())
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new DisposableObserver<Contributor>() {
                   @Override
                   public void onNext(Contributor contributor) {
                     contributionMap.put(contributor.login,contributor.contributions);
                       adapterDetail.clear();
                       adapterDetail.addAll(mapAsList(contributionMap));

                   }

                   @Override
                   public void onError(Throwable e) {
                       Timber.e(e,"arr something went wrong");

                   }

                   @Override
                   public void onComplete() {
                       Timber.d("done loading all data");
                   }
               });
   }

    /**
     * starts both observable but buffers the result from
     * the latter one until the former Observable finished
     */
   @OnClick(R.id.btnPseudoCacheContatEager)
   public void onCancatEagerClick(){
       setPseudoCacheInfo("Concat Eager");
       wireupDemo();

       List<Observable<Contributor>> observables=new ArrayList<>();
       observables.add(getSlowCacheDiskData());
       observables.add(getFreshNetworkData());

       Observable.concatEager(observables)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new DisposableObserver<Contributor>() {
                   @Override
                   public void onNext(Contributor contributor) {
                       contributionMap.put(contributor.login,contributor.contributions);
                       adapterDetail.clear();
                       adapterDetail.addAll(mapAsList(contributionMap));

                   }

                   @Override
                   public void onError(Throwable e) {
                       Timber.e(e,"arr something went wrong");

                   }

                   @Override
                   public void onComplete() {
                       Timber.d("done loading all data");

                   }
               });

   }

    /**
     * Merge: interleaves items as they are emiited
     */
   @OnClick(R.id.btnPseudoCacheMerge)
   public void onMergeClicked(){
       setPseudoCacheInfo("PseudoCache Merge");

       wireupDemo();
       Observable.merge(getCachedDiskData(1),getCachedDiskData(2))
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new DisposableObserver<Contributor>() {
                   @Override
                   public void onNext(Contributor contributor) {
                       contributionMap.put(contributor.login,contributor.contributions);
                       adapterDetail.clear();
                       adapterDetail.addAll(mapAsList(contributionMap));
                   }

                   @Override
                   public void onError(Throwable e) {
                       Timber.e(e,"arr something went wrong");

                   }

                   @Override
                   public void onComplete() {
                       Timber.d("done loading all data");

                   }
               });

   }

   @OnClick(R.id.btnPseudoCacheMergeSlowDisk)
   public void onPseudoCacheMergeSlowDisk(){
       setPseudoCacheInfo("Merge Slow Disk");
       wireupDemo();

       Observable.merge(getSlowCacheDiskData(),getFreshNetworkData())
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new DisposableObserver<Contributor>() {
                   @Override
                   public void onNext(Contributor contributor) {
                       contributionMap.put(contributor.login,contributor.contributions);
                       adapterDetail.clear();
                       adapterDetail.addAll(mapAsList(contributionMap));

                   }

                   @Override
                   public void onError(Throwable e) {
                       Timber.e(e,"arr something went wrong");

                   }

                   @Override
                   public void onComplete() {
                       Timber.d("done loading all data");

                   }
               });

   }

   @OnClick(R.id.btnPseudoCacheMergeOptimized)
   public void onMergeOptimizedClicked(){
       setPseudoCacheInfo("Merge Optimized");
       wireupDemo();
       getFreshNetworkData().publish(new Function<Observable<Contributor>, ObservableSource<Contributor>>() {
           @Override
           public ObservableSource<Contributor> apply(@NonNull Observable<Contributor> contributorObservable) throws Exception {
               return Observable.merge(contributorObservable,getCachedDiskData(1).takeUntil(contributorObservable));
           }
       })

   }

   private List<String> mapAsList(HashMap<String,Long> map){
       List<String> list=new ArrayList<>();
       for (String username : map.keySet()) {
           String rowLog=String.format("%s [%d]",username,contributionMap.get(username));
           list.add(rowLog);
       }
       return list;
   }

   private Observable<Contributor> getFreshNetworkData(){

       return Observable.timer(2, TimeUnit.SECONDS)
               .flatMap(new Function<Long, ObservableSource<Contributor>>() {
                   @Override
                   public ObservableSource<Contributor> apply(@NonNull Long aLong) throws Exception {
                       return getCachedDiskData(2);
                   }
               });
//       String githubToken="";
//       GithubApi gitgubService= GithubService.createGithubService(githubToken);
//       return gitgubService
//               .contributors("square","retrofit")
//               .flatMap(new Function<List<Contributor>, ObservableSource<Contributor>>() {
//                   @Override
//                   public ObservableSource<Contributor> apply(@NonNull List<Contributor> contributors) throws Exception {
//                       return Observable.fromIterable(contributors);
//                   }
//               }).doOnSubscribe(new Consumer<Disposable>() {
//                   @Override
//                   public void accept(@NonNull Disposable disposable) throws Exception {
//                       new Handler(Looper.getMainLooper())
//                               .post(new Runnable() {
//                                   @Override
//                                   public void run() {
//                                       adapterSubscriptionInfo.add("(network) subscribed");
//                                   }
//                               });
//                   }
//               }).doOnComplete(new Action() {
//                   @Override
//                   public void run() throws Exception {
//                       new Handler(Looper.getMainLooper())
//                               .post(new Runnable() {
//                                   @Override
//                                   public void run() {
//                                       adapterSubscriptionInfo.add("(network) completed");
//                                   }
//                               });
//                   }
//               });
   }

    private Observable<Contributor> getSlowCacheDiskData() {
        return Observable.timer(5, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<Contributor>>() {
                    @Override
                    public ObservableSource<Contributor> apply(@NonNull Long aLong) throws Exception {
                        return getCachedDiskData(1);
                    }
                });
    }

    private Observable<Contributor> getCachedDiskData(int type){
        List<Contributor> list=new ArrayList<>();
        Map<String,Long> map=dummyDiskData(type);

        for (String username : map.keySet()) {
            Contributor c=new Contributor();
            c.login=username;
            c.contributions=map.get(username);
            list.add(c);
        }

        return Observable.fromIterable(list)
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(@NonNull Disposable disposable) throws Exception {
                        new Handler(Looper.getMainLooper())
                                .post(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterSubscriptionInfo.add("(disk) cache subscribed");
                                    }
                                });
                    }
                }).doOnComplete(new Action() {
                    @Override
                    public void run() throws Exception {
                        new Handler(Looper.getMainLooper())
                                .post(new Runnable() {
                                    @Override
                                    public void run() {

                                        adapterSubscriptionInfo.add("(disk) cache completed");

                                    }
                                });
                    }
                });
    }

    private Map<String, Long> dummyDiskData(int type) {
        Map<String,Long> map=new HashMap<>();
        if(type==1) {
            map.put("JakeWharton", 0L);
            map.put("pforhan", 0L);
            map.put("edenman", 0L);
            map.put("swankjesse", 0L);
            map.put("bruceLee", 0L);
        }else{
            map.put("J", 0L);
            map.put("P", 0L);
            map.put("E", 0L);
            map.put("S", 0L);
            map.put("B", 0L);
        }
        return map;
    }

    private void wireupDemo() {
        contributionMap=new HashMap<>();
        adapterDetail=new ArrayAdapter<String>(getActivity(),
                R.layout.item_log_white,R.id.item_log,new ArrayList<String>());
        lstPseudoCacheDetail.setAdapter(adapterDetail);

        adapterSubscriptionInfo=new ArrayAdapter<String>(getActivity(),
                R.layout.item_log_white,R.id.item_log,new ArrayList<String>());
        lstPseudoCacheListSubscription.setAdapter(adapterSubscriptionInfo);

    }

    private void setPseudoCacheInfo(String msgString) {
        tvPseudoCacheInfo.setText(msgString);
    }

}
