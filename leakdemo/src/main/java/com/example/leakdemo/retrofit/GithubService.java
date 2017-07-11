package com.example.leakdemo.retrofit;

import android.text.TextUtils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;

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

    public static GithubApi createGithubService(final String githubToken){
        Retrofit.Builder builder=
                new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl);

        if (!TextUtils.isEmpty(githubToken)) {

            OkHttpClient client=
                    new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request=chain.request();
                            Request newReq=request
                                    .newBuilder()
                                    .addHeader("Authorization",format("token %s",githubToken))
                                    .build();
                            return chain.proceed(newReq);
                        }
                    }).build();
            builder.client(client);
        }

        return builder.build().create(GithubApi.class);
    }

}
