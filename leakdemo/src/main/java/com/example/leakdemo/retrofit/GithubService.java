package com.example.leakdemo.retrofit;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * __   __    _
 * \ \ / /_ _| |_   _ _ __
 * \ V / _` | | | | | '_ \
 * | | (_| | | |_| | | | |
 * |_|\__,_|_|\__,_|_| |_|
 * Created by Hades on 2017/7/6.
 */

public class GithubService {

    private final static String baseUrl="https://api.github.com";

    private GithubService(){}

    public static void createGithubService(final String githubToken){
        Retrofit.Builder builder=
                new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl);

        if (!TextUtils.isEmpty(githubToken)) {

        }
    }

}
