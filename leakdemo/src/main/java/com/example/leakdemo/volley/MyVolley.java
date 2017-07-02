package com.example.leakdemo.volley;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by zhanghehe on 2017/7/2.
 */

public class MyVolley {
    private static RequestQueue mRequestQueue;

    private MyVolley(){}

    public static void init(Context context){
        mRequestQueue= Volley.newRequestQueue(context);
    }

    static RequestQueue getRequestQueue(){
        if(mRequestQueue != null){
            return mRequestQueue;
        }else{
            throw new IllegalStateException("Volley RequestQueue not initialed");
        }
    }
}
