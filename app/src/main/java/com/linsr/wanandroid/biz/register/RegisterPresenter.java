package com.linsr.wanandroid.biz.register;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.linlibrary.net.NetObserver;
import com.linsr.linlibrary.net.RxHelper;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.base.ApplicationEx;
import com.linsr.wanandroid.biz.auth.Auth3Activity;
import com.linsr.wanandroid.biz.auth.AuthActivity;
import com.linsr.wanandroid.biz.login.LoginActivity;
import com.linsr.wanandroid.biz.main.MainActivity;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.model.UserPojo;
import com.linsr.wanandroid.data.remote.Api;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import java.util.Map;

import okhttp3.RequestBody;

import static com.linsr.wanandroid.utils.Utils.isPhoneNumber;
import static com.linsr.wanandroid.utils.Utils.md5;

/**
 * Description
 @author Linsr
 */

public class RegisterPresenter implements RegisterContact.Presenter {

    private static final String TAG = "RegisterPresenter";

    private RegisterContact.View mView;
    private CountDownTime mCountDownTime;
    private Activity mActivity;
    private WanPreferences mWanPreferences;

    public RegisterPresenter(Activity activity, RegisterContact.View view) {
        mActivity = activity;
        mWanPreferences = WanPreferences.getInstance();
        mCountDownTime = new CountDownTime(60 * 1000, 1 * 1000);
        mView = view;
    }

    @Override
    public boolean paramsValid(String phone, String pwd1, String pwd2) {
        if (!isPhoneNumber(phone)) {
            return false;
        }
        if (TextUtils.isEmpty(pwd1) || TextUtils.isEmpty(pwd2)) {
            return false;
        }
        if (!TextUtils.equals(pwd1, pwd2)) {
            return false;
        }
        return true;
    }

    @Override
    public void doRegister(final String phone, final String password, String code) {

        Api.request().register(phone, md5(password), code)
                .compose(RxHelper.<ResponsePojo<UserPojo>>ioMain())
                .compose(RxHelper.<UserPojo>handleResponse())
                .subscribe(new NetObserver<UserPojo>(mActivity, true) {
                    @Override
                    public void onSucceed(UserPojo data) {
                        WanPreferences.getInstance().setUserCode(data.getUser_code());
                        ToastUtils.toast(mActivity, "注册成功");
                        login(phone, password);
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    private void login(String phoneNumber, String password) {
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

//                        if (TextUtils.equals(data.getCertified_status(), "b")) {
//                            Intent intent = new Intent(mActivity, MainActivity.class);
//                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                            mActivity.startActivity(intent);
//                        } else {
                            mActivity.startActivity(new Intent(mActivity, AuthActivity.class));
//                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                    }
                });
    }

    @Override
    public void identifyingCode(String phone) {
        mCountDownTime.start();
        Api.request().identifyingCode(phone)
                .compose(RxHelper.<ResponsePojo>ioMain())
                .compose(RxHelper.handleResponse1())
                .subscribe(new NetObserver<ResponsePojo>() {
                    @Override
                    public void onSucceed(ResponsePojo data) {
                        ToastUtils.toast(mActivity, "验证码发送成功");
                    }

                    @Override
                    public void onFailed(String msg) {
                        ToastUtils.toast(mActivity, msg);
                        mCountDownTime.cancel();
                        mCountDownTime.onFinish();
                    }
                });
    }


    /**
     * 第一种方法 使用android封装好的 CountDownTimer
     * 创建一个类继承 CountDownTimer
     */
    private class CountDownTime extends CountDownTimer {

        //构造函数  第一个参数代表总的计时时长  第二个参数代表计时间隔  单位都是毫秒
        CountDownTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) { //每计时一次回调一次该方法
            mView.setCodeBtn(false, l / 1000 + "秒后重新开始");
        }

        @Override
        public void onFinish() { //计时结束回调该方法
            mView.setCodeBtn(true, "发送验证码");
        }
    }

}
