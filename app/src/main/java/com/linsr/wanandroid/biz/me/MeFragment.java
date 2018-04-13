package com.linsr.wanandroid.biz.me;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseFragment;
import com.linsr.wanandroid.biz.login.LoginActivity;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.model.UserPojo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 @author Linsr
 */

public class MeFragment extends BaseFragment {

    @BindView(R.id.me_user_name_tv)
    TextView mUserName;

    public static MeFragment newInstance() {

        Bundle args = new Bundle();

        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        Api.request().getUserInfo(mUserCode)
                .compose(RxHelper.<ResponsePojo<UserPojo>>ioMain())
                .compose(RxHelper.<UserPojo>handleResponse())
                .subscribe(new NetObserver<UserPojo>() {
                    @Override
                    public void onSucceed(UserPojo data) {
                        mUserName.setText(data.getUser_name());
                    }

                    @Override
                    public void onFailed(String msg) {

                    }
                });
    }

    @Override
    protected void initView() {
        JLog.a("knowledge");
    }

    @OnClick(R.id.me_logout)
    void onLogout() {
        Api.request().logout(mUserCode)
                .compose(RxHelper.<ResponsePojo<Object>>ioMain())
                .compose(RxHelper.handleResponse())
                .subscribe(new NetObserver<Object>() {
                    @Override
                    public void onSucceed(Object data) {
                        ToastUtils.toast(mActivity, "注销成功");
                        WanPreferences.getInstance().logout();
                        startActivity(new Intent(mActivity, LoginActivity.class));
                        mActivity.finish();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, "登出失败");
                    }
                });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }
}
