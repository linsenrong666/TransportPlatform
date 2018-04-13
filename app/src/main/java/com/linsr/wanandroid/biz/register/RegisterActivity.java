package com.linsr.wanandroid.biz.register;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.utils.ToastUtils;
import com.linsr.wanandroid.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Description
 @author Linsr
 */

public class RegisterActivity extends BaseActivity implements RegisterContact.View {

    @BindView(R.id.register_code_tv)
    TextView mCodeTextView;
    @BindView(R.id.register_phone)
    EditText mPhoneEditText;
    @BindView(R.id.register_pwd_1)
    EditText mPwd1EditText;
    @BindView(R.id.register_pwd_2)
    EditText mPwd2EditText;
    @BindView(R.id.register_code)
    EditText mCodeEditText;

    private RegisterPresenter mRegisterPresenter;

    @Override
    protected void init(Bundle savedInstanceState) {
        mRegisterPresenter = new RegisterPresenter(this,this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.acitivyt_register;
    }

    @OnClick(R.id.register_left_arrow)
    void onBack() {
        onBackPressed();
    }

    @OnClick(R.id.register_code_tv)
    void onCode() {
        String phone = mPhoneEditText.getText().toString().trim();
        mRegisterPresenter.identifyingCode(phone);
    }

    @OnClick(R.id.register_confirm)
    void onConfirm() {
        String phone = mPhoneEditText.getText().toString().trim();
        String pwd1 = mPwd1EditText.getText().toString().trim();
        String pwd2 = mPwd2EditText.getText().toString().trim();
        String code = mCodeEditText.getText().toString().trim();
        if (!mRegisterPresenter.paramsValid(phone, pwd1, pwd2)) {
            ToastUtils.toast(this,"输入信息有误");
            return;
        }
        mRegisterPresenter.doRegister(phone,pwd1, code);
    }

    @Override
    public void setCode(String phone) {
        mCodeEditText.setText(phone);
    }

    @Override
    public void setCodeBtn(boolean canClick, String text) {
        mCodeTextView.setClickable(canClick);
        mCodeTextView.setText(text);
    }


}
