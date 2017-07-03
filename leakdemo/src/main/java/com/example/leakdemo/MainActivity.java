package com.example.leakdemo;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.leakdemo.fragments.MainFragment;
import com.example.leakdemo.rxbus.RxBus;

public class MainActivity extends AppCompatActivity {


    private RxBus rxBus;

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
        if(rxBus==null){
            rxBus=new RxBus();
        }
        return rxBus;

    }

    private void removeWorkerFragments(){
        Fragment frag=getSupportFragmentManager()
                .findFragmentByTag(RotationPersist1WorkerFragment.class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();
        }

        frag=getSupportFragmentManager().findFragmentByTag(RotationPersist2WorkerFragment
        .class.getName());
        if (frag != null) {
            getSupportFragmentManager().beginTransaction().remove(frag).commit();

        }

    }

}
