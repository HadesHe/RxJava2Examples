package com.example.leakdemo.retrofit;

import com.example.leakdemo.bean.Contributor;
import com.example.leakdemo.bean.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zhanghehe on 2017/7/8.
 */

public interface GithubApi {

    @GET("/repos/{owner}/{repo}/contributors")
    Observable<List<Contributor>> contributors(
            @Path("owner")String owner,@Path("repo") String repo
    );

    @GET("/repos/{owner}/{repo}/contributors")
    List<Contributor> getContributors(@Path("owner")String owner,@Path("repo")String repo);

    @GET("/users/{user}")
    Observable<User> user(@Path("user")String user);

    @GET("/users/{user}")
    User getUser(@Path("user") String user);


}
