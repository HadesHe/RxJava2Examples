package com.example.leakdemo;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState==null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(android.R.id.content,new MainFragment(),this.toString())
                    .commit();
        }
    }

    public RxBus getRxBusSingleton(){

    }

}
