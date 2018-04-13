package com.linsr.wanandroid.biz.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.biz.register.RegisterActivity;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 @author Linsr
 */

public class LoginActivity extends BaseActivity implements LoginContact.View {

    @BindView(R.id.login_phone)
    EditText mPhoneEditText;
    @BindView(R.id.login_password)
    EditText mPwdEditText;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mLoginPresenter = new LoginPresenter(this, this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.login_create)
    void onCreate() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.login_btn)
    void onLogin() {
        String phone = mPhoneEditText.getText().toString().trim();
        String pwd = mPwdEditText.getText().toString().trim();
        if (!Utils.isPhoneNumber(phone)) {
            ToastUtils.toast(this,"请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            ToastUtils.toast(this,"请输入密码");
            return;
        }

        mLoginPresenter.login(phone, pwd);
    }

    @OnClick(R.id.login_find_pwd)
    void onFind() {
        mLoginPresenter.logout(WanPreferences.getInstance().getUserCode());
    }

}
