package com.example.leakdemo.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.leakdemo.R;
import com.example.leakdemo.base.BaseFragment;
import com.jakewharton.rxbinding2.widget.RxTextView;

import butterknife.BindView;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function3;
import io.reactivex.subscribers.DisposableSubscriber;
import timber.log.Timber;

import static android.util.Patterns.EMAIL_ADDRESS;

/**
 * Created by zhanghehe on 2017/7/19.
 */

public class FormValidationCombineLatesFragment extends BaseFragment {

    @BindView(R.id.etCombEmail)
    EditText etCombEmail;
    @BindView(R.id.etCombPassword)
    EditText etCombPassword;
    @BindView(R.id.etCombNum)
    EditText etCombNum;

    @BindView(R.id.btnDemoValidForm)
    Button btnDemoValidForm;
    private Flowable<CharSequence> emailChangeObservable;
    private Flowable<CharSequence> passwordChangeObservable;
    private Flowable<CharSequence> numChangeObservable;
    private DisposableSubscriber<Boolean> disposableObserver;

    @Override
    public int layoutResId() {
        return R.layout.fragment_fromvalidation_combine;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailChangeObservable= RxTextView.textChanges(etCombEmail)
                .skip(1)
                .toFlowable(BackpressureStrategy.LATEST);
        passwordChangeObservable=RxTextView.textChanges(etCombPassword)
                .skip(1)
                .toFlowable(BackpressureStrategy.LATEST);
        numChangeObservable=RxTextView.textChanges(etCombNum)
                .skip(1)
                .toFlowable(BackpressureStrategy.LATEST);
        combineLatestEvents();
    }

    private void combineLatestEvents() {
        disposableObserver=
                new DisposableSubscriber<Boolean>() {
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            btnDemoValidForm.setBackgroundColor(
                                    ContextCompat.getColor(getContext(),R.color.colorAccent)
                            );
                        }else{
                            btnDemoValidForm.setBackgroundColor(
                                    ContextCompat.getColor(getContext(),R.color.colorPrimaryDark)
                            );
                            
                        }
                        
                    }

                    @Override
                    public void onError(Throwable t) {
                        Timber.e(t,"there was an error");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("completed");
                    }
                };
                
                Flowable.combineLatest(emailChangeObservable,
                        passwordChangeObservable,
                        numChangeObservable, new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
                            @Override
                            public Boolean apply(@NonNull CharSequence emailString, @NonNull CharSequence passwordString, @NonNull CharSequence numString) throws Exception {
                                boolean emailValid= !TextUtils.isEmpty(emailString)&&EMAIL_ADDRESS.matcher(emailString).matches();
                                if (!emailValid) {
                                    etCombEmail.setError("Invalid Email!");
                                }
                                boolean passwordValid= !TextUtils.isEmpty(passwordString)&&passwordString.length()>8;
                                if (!passwordValid) {
                                    etCombPassword.setError("Invalid password");
                                }
                                boolean numValid= !TextUtils.isEmpty(numString);
                                if(numValid){
                                    int num=Integer.parseInt(numString.toString());
                                    numValid=num>0&&num<=100;
                                }
                                if (!numValid) {
                                    etCombNum.setError("Invalid Number!");
                                }
                                return emailValid&&passwordValid&&numValid;
                            }
                        }).subscribe(disposableObserver);
    }
}
