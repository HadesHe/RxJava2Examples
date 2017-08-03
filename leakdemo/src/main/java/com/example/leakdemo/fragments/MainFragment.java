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

    @OnClick(R.id.btnDemoPagination)
    void onPagination(){
        clickedOn(new PaginationAutoFragment());

    }

    @OnClick(R.id.btnDemoNetworkDetrctor)
    void onNetworkDetector(){
        clickedOn(new NetworkDetectorFragment());

    }

    @OnClick(R.id.btnSimpleExample)
    void onSimpleExampleClick(){
        clickedOn(new SimpleExampleFragment());
    }

    // TODO: 2017/8/3 MapExample
    @OnClick(R.id.btnMapExample)
    void onMapExampleClick(){

    }

    @OnClick(R.id.btnDisposableExample)
    void onDisposableExample(){
        clickedOn(new DisposableExample());
    }

    @OnClick(R.id.btnFlowableExample)
    void onFlowableExampleClick(){
        clickedOn(new FlowableFragment());
    }

    // TODO: 2017/8/3 ZipExample
    void onZipExampleClick(){

    }

    @OnClick(R.id.btnSingelObserverExample)
    void onSingleObserverClick(){
        clickedOn(new SingleObserverFragment());
    }

    // TODO: 2017/8/3 completableObserver
    @OnClick(R.id.btnCompletableObserverExample)
    void onCompletableObserverClick(){
        clickedOn(new SingleObserverFragment());
    }

    // TODO: 2017/8/3 BufferExample
    void onBufferExample(){

    }

    // TODO: 2017/8/3 TakeExample
    void onTakeExample(){

    }

    // TODO: 2017/8/3 ReduceExample
    void onReduceExample(){}

    // TODO: 2017/8/3  FilterExample
    void onFilterExample(){}

    // TODO: 2017/8/3 SkipExample
    void onSKipExample(){}

    // TODO: 2017/8/3 ReplayExample
    void onReplayExample(){}

    // TODO: 2017/8/3 ConcatExample
    void onCancatExample(){}





    private void clickedOn(Fragment fragment){
        final String tag=fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content,fragment,tag)
                .commit();
    }

}
