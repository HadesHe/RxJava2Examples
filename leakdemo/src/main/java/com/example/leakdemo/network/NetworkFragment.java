package com.example.leakdemo.network;


import android.util.Log;
import android.util.Pair;

import com.example.leakdemo.R;
import com.example.leakdemo.bean.User;
import com.example.leakdemo.fragments.Base2Fragment;
import com.example.leakdemo.model.ApiUser;
import com.example.leakdemo.model.UserDetail;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
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
                return filterUserWhoLovesBoth(userList,userList2);
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

    private ObservableSource<? extends List<User>> getFootballFabsObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFootballFans")
                .build()
                .getObjectListObservable(User.class);
    }

    private Observable< List<User>> getCircketFansObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllCricketFans")
                .build()
                .getObjectListObservable(User.class);
    }

    private List<User> filterUserWhoLovesBoth(List<User> userList, List<User> userList2) {
        List<User> usersWhoLovesBoth=new ArrayList<>();
        for (User user : userList) {
            for (User user1 : userList2) {
                if(user.id==user1.id){
                    usersWhoLovesBoth.add(user);
                }
            }
        }
        return usersWhoLovesBoth;
    }

    @OnClick(R.id.btnNetworkFlatMapAndFilter)
    public void onFlatMapAndFilterClick(){
        getAllMyFriendsObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(@NonNull List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .filter(new Predicate<User>() {
                    @Override
                    public boolean test(@NonNull User user) throws Exception {
                        return user.isFollowing;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver("flatmapandfilter"));

    }

    private Observable<List<User>> getAllMyFriendsObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFriends/{userId}")
                .addPathParameter("userId","1")
                .build()
                .getObjectListObservable(User.class);
    }

    @OnClick(R.id.btnNetworkTake)
    public void onTakeClick(){
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {

                    @Override
                    public ObservableSource<User> apply(@NonNull List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .take(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver("take"));

    }

    private Observable<List<User>> getUserListObservable() {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
                .addPathParameter("pageNumber","0")
                .addQueryParameter("limit","10")
                .build()
                .getObjectListObservable(User.class);
    }

    @OnClick(R.id.btnNetworkFlatMap)
    public void onFlatMapClick(){
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(@NonNull List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                })
                .flatMap(new Function<User, ObservableSource<UserDetail>>() {
                    @Override
                    public ObservableSource<UserDetail> apply(@NonNull User user) throws Exception {
                        return getUserDetailObservable(user.id);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserDetail>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserDetail userDetail) {
                        Log.d("flatmap","onNext"+userDetail.toString());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("flatmap","onError"+e.toString());

                    }

                    @Override
                    public void onComplete() {
                        Log.d("flatmap","onComplete");
                    }
                });

    }

    private ObservableSource<UserDetail> getUserDetailObservable(long id) {
        return Rx2AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
                .addPathParameter("userId",String.valueOf(id))
                .build()
                .getObjectObservable(UserDetail.class);
    }

    @OnClick(R.id.btnNetworkFlatMapWithZip)
    public void onFlatMapWithZip(){
        getUserListObservable()
                .flatMap(new Function<List<User>, ObservableSource<User>>() {
                    @Override
                    public ObservableSource<User> apply(@NonNull List<User> users) throws Exception {
                        return Observable.fromIterable(users);
                    }
                }).flatMap(new Function<User, ObservableSource<Pair<UserDetail,User>>>() {

            @Override
            public ObservableSource<Pair<UserDetail, User>> apply(@NonNull User user) throws Exception {
                return Observable.zip(getUserDetailObservable(user.id),
                        Observable.just(user),
                        new BiFunction<UserDetail, User, Pair<UserDetail, User>>() {
                            @Override
                            public Pair<UserDetail, User> apply(@NonNull UserDetail userDetail, @NonNull User user) throws Exception {
                                return new Pair<UserDetail, User>(userDetail,user);
                            }
                        });
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Pair<UserDetail, User>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Pair<UserDetail, User> userDetailUserPair) {

                        Log.d("flatmapwithzip","user"+userDetailUserPair.second);
                        Log.d("flatmapwithzip","userDetail"+userDetailUserPair.first);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
