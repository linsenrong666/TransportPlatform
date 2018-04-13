package com.linsr.wanandroid.biz.login;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.model.UserPojo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.biz.main.MainActivity;
import com.linsr.wanandroid.biz.auth.AuthActivity;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import okhttp3.RequestBody;

/**
 * Description
 @author Linsr
 */

public class LoginPresenter implements LoginContact.Presenter {

    private static final String TAG = "LoginPresenter";

    private WanPreferences mWanPreferences;
    private LoginContact.View mView;
    private Activity mActivity;

    public LoginPresenter(Activity activity,
                          LoginContact.View view) {
        mView = view;
        mActivity = activity;
        mWanPreferences = WanPreferences.getInstance();
    }

    @Override
    public void login(String phoneNumber, String password) {
        UserPojo u = new UserPojo();
        u.setPhone_number(phoneNumber);
        u.setPassword(Utils.md5(password));
        Gson gson = new Gson();
        String obj = gson.toJson(u);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), obj);

        Api.request().login(body)
                .compose(RxHelper.<ResponsePojo<UserPojo>>ioMain())
                .compose(RxHelper.<UserPojo>handleResponse())
                .subscribe(new NetObserver<UserPojo>(mActivity, true) {
                    @Override
                    public void onSucceed(UserPojo data) {
                        JLog.i(TAG, "登陆成功", "phone:" + data.getPhone_number(), "token:" + data.getToken());
                        String token = data.getToken();
                        mWanPreferences.setAccessToken(token);
                        mWanPreferences.setUserCode(data.getUser_code());
                        mWanPreferences.setUserPhone(data.getPhone_number());
                        mWanPreferences.setUserDriving(data.getDriving_license());
                        mWanPreferences.setUserIdCard(data.getId_card());
                        mWanPreferences.setUserVehicle(data.getVehicle_license());
                        mWanPreferences.setUserAuthStatus(data.getCertified_status());

                        if (TextUtils.equals(data.getCertified_status(), "b")) {
                            mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                        } else {
                            mActivity.startActivity(new Intent(mActivity, AuthActivity.class));
                        }
                        mActivity.finish();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    @Override
    public void logout(String userCode) {

        Api.request().logout(userCode)
                .compose(RxHelper.<ResponsePojo<Object>>ioMain())
                .compose(RxHelper.handleResponse())
                .subscribe(new NetObserver<Object>() {
                    @Override
                    public void onSucceed(Object data) {
                        ToastUtils.toast(mActivity, "登出成功");
                        mWanPreferences.logout();
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, "登出失败");
                    }
                });
    }

}
