package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.pagination.PaginationAutoFragment;
import com.example.leakdemo.volley.VolleyDemoFragment;

import butterknife.OnClick;

/**
 * Created by zhanghehe on 2017/7/3.
 */

public class MainFragment extends BaseFragment {


    @Override
    public int layoutResId() {
        return R.layout.fragment_main;
    }



    @OnClick(R.id.btnDemoSchedulers)
    void concurrencyWithSchedulers(){
        clickedOn(new ConcurrencyWithSchedulersDemoFragment());
    }

    @OnClick(R.id.btnDemoVolley)
    void volleyRequestDemo(){
        clickedOn(new VolleyDemoFragment());
    }

    @Deprecated
    @OnClick(R.id.btnDemoBuffer)
    void bufferDemo(){
//        clickedOn(new BufferFragmen());
    }

    @OnClick(R.id.btnDemoDebounce)
    void debounceDemo(){
        clickedOn(new DebounceSearchEmitterFrament());
    }

    @OnClick(R.id.btnDemoPolling)
    void pollingDemo(){
        clickedOn(new PollingFragment());
    }

    @OnClick(R.id.btnDemoExponentialBackoff)
    void demoExponentialBackoff(){
        clickedOn(new ExponentialBackOffFragment());
    }

    @OnClick(R.id.btnDemoFormValidationCombinel)
    void onFormValidationCombine(){
        clickedOn(new FormValidationCombineLatesFragment());
    }

    @OnClick(R.id.btnDemoPseudoCache)
    void onPseudoCache(){
        clickedOn(new PseidpCacheFragment());

    }

    @OnClick(R.id.btnDemoRxbus)
    void onRxbus(){
        clickedOn(new RxBusFragment());
    }

    @OnClick(R.id.btnDemoTiming)
    void onTiming(){
        clickedOn(new TimingDemoFragment());
    }

    @OnClick(R.id.btnDemoRotationPersist)
    void onRotationPersistClick(){
        // TODO: 2017/7/30 switch different Rotation
        clickedOn(new RotationPersist3Fragment());
//        clickedOn(new RotationPersist2Fragment());
//        clickedOn(new RotationPersist1Fragment());
    }




    @OnClick(R.id.btnDemoTimeout)
    void onTimingOut(){
        clickedOn(new TimingOutFragment());
    }

    // TODO: 2017/7/31 onPagination
    @OnClick(R.id.btnDemoPagination)
    void onPagination(){
        clickedOn(new PaginationAutoFragment());
        // TODO: 2017/8/1  clickedOn(new PaginationFragment());

    }

    // TODO: 2017/7/31 onNetworkDetector
    @OnClick(R.id.btnDemoNetworkDetrctor)
    void onNetworkDetector(){

    }

    // TODO: 2017/7/31 onMulticastPlayGround
    @OnClick(R.id.btnMulticastPlayGround)
    void onMulticastPlayGround(){

    }




    private void clickedOn(Fragment fragment){
        final String tag=fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content,fragment,tag)
                .commit();
    }

}
