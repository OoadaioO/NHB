package com.nhb.app.custom.ui.personal.fragment;

import android.content.Intent;
import android.databinding.Observable;

import com.nhb.app.custom.R;
import com.nhb.app.custom.base.BaseFragment;
import com.nhb.app.custom.databinding.FragmentPersonalBinding;
import com.nhb.app.custom.ui.personal.LoginActivity;
import com.nhb.app.custom.viewmodel.PersonalFragmentVM;

/**
 * ***********************************************************************
 * Author:pengxiaofang
 * CreateData:2016-06-29 17:03
 * Version:xx
 * Description:xx
 * ***********************************************************************
 */
public class PersonalFragment extends BaseFragment<PersonalFragmentVM, FragmentPersonalBinding> {
    @Override
    protected PersonalFragmentVM loadViewModel() {
        return new PersonalFragmentVM();
    }

    @Override
    protected int loadLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initialize() {
        viewModel.tokenInvalid.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                startLogin();
            }
        });
        viewModel.fetchRemoteData(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.fetchRemoteData(false);
    }

    /**
     * 启动登录流程
     */
    public void startLogin() {
        startActivity(new Intent(mContext, LoginActivity.class));
        transitionWithBottomEnter();
    }
}
