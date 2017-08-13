package com.example.leakdemo.fragments;

import android.support.v4.app.Fragment;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.example.leakdemo.network.NetworkFragment;
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

    @OnClick(R.id.btnMapExample)
    void onMapExampleClick(){
        clickedOn(new MapExampleFragnent());

    }

    @OnClick(R.id.btnZipExample)
    void onZipExampleClick(){
        clickedOn(new ZipExampleFragment());

    }

    @OnClick(R.id.btnDisposableExample)
    void onDisposableExample(){
        clickedOn(new DisposableExample());
    }

    // TODO: 2017/8/3 TakeExample
    @OnClick(R.id.btnTakeExample)
    void onTakeExample(){
        clickedOn(new TakeExampleFragment());

    }

    @OnClick(R.id.btnTimerExample)
    void onTimerExampleClick(){
        clickedOn(new TimerExampleFragment());
    }

    @OnClick(R.id.btnIntervalExample)
    void onIntervalExampleClick(){
        clickedOn(new IntervalExampleFragment());

    }

    @OnClick(R.id.btnSingelObserverExample)
    void onSingleObserverClick(){
        clickedOn(new SingleObserverFragment());
    }


    // TODO: 2017/8/3 completableObserver
    @OnClick(R.id.btnCompletableObserverExample)
    void onCompletableObserverClick(){
        clickedOn(new CompletableObserverExample());
    }

    @OnClick(R.id.btnFlowableExample)
    void onFlowableExampleClick(){
        clickedOn(new FlowableFragment());
    }

    @OnClick(R.id.btnReduceExample)
    void onReduceExample(){
        clickedOn(new ReduceFragment());
    }

    @OnClick(R.id.btnBufferExample)
    void onBufferExampleClick(){
        clickedOn(new BufferExampleFragment());
    }

    @OnClick(R.id.btnFilterExample)
    void onFilterExampleClick(){
        clickedOn(new FilterExampleFragment());
    }

    @OnClick(R.id.btnSkipExample)
    void onSkipExampleClick(){
        clickedOn(new SkipExampleFragment());

    }

    @OnClick(R.id.btnScanExample)
    void onScanExampleClick(){
        clickedOn(new ScanExampleFragment());
    }

    @OnClick(R.id.btnReplayExample)
    void onReplayExampleClick(){
        clickedOn(new ReplayExampleFragment());
    }

    @OnClick(R.id.btnConcatExample)
    void onConcatExampleClick(){
        clickedOn(new ConcatExampleActivity());
    }

    @OnClick(R.id.btnMergeExample)
    void onMergeExampleClick(){
        clickedOn(new MergeExampleFragment());
    }

    @OnClick(R.id.btnDeferExample)
    void onDeferExampleClick(){
        clickedOn(new DeferExampleFragment());
    }

    @OnClick(R.id.btnDistinctExample)
    void onDistinctExampleClick(){
        clickedOn(new DistinctExampleFragment());
    }

    @OnClick(R.id.btnLastOperatorExample)
    void onLastOperatorClick(){
        clickedOn(new LastOperatiorFragment());
    }

    @OnClick(R.id.btnReplaySubjectExample)
    void onReplaySubjecyClick(){
        clickedOn(new ReplaySubjectExample());
    }

    @OnClick(R.id.btnPulishSubjectExample)
    void onPulishSubjectClick(){

        clickedOn(new PulishSubjectFragment());
    }




    @OnClick(R.id.btnBehaviorSubjectExample)
    void onBehaviorSubjectClick(){
        clickedOn(new BeHaviorSubjectExmaple());

    }

    @OnClick(R.id.btnAsynSubjectExample)
    void onAsyncSubjectClick(){
        clickedOn(new AsyncSubjectExample());
    }

    //间隔内的第一个
    @OnClick(R.id.btnThrottleFirstExample)
    void onThrottleFirstClick(){
        clickedOn(new ThrottleFirstExmaple());
    }

    //间隔内的最后一个
    @OnClick(R.id.btnThrottleLastExample)
    void onThrottleLastClick(){
        clickedOn(new ThrottleLastExmaple());
    }

    @OnClick(R.id.btnDebounceExample)
    void onDebounceClick(){
        clickedOn(new DebounceFragment());
    }

    @OnClick(R.id.btnWindowExample)
    void onWindowClick(){}

    @OnClick(R.id.btnDelayExample)
    void onDelayClick(){
        clickedOn(new DelayExmapke());
    }

    @OnClick(R.id.btnNetworkExample)
    void onNetworkExample(){
        clickedOn(new NetworkFragment());
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
