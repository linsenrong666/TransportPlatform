package com.linsr.wanandroid.biz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.biz.auth.AuthActivity;
import com.linsr.wanandroid.biz.login.LoginActivity;
import com.linsr.wanandroid.biz.main.MainActivity;
import com.linsr.wanandroid.data.local.WanPreferences;

/**
 * Description
 @author Linsr
 */

public class SplashActivity extends BaseActivity implements Runnable {

    private static final long SPLASH_SCREEN_WAITING_TIME = 1 * 1000;
    private boolean mIsLogined;
    private boolean mIsAuthed;

    @Override
    protected void init(Bundle savedInstanceState) {
        String token = WanPreferences.getInstance().getAccessToken();
        JLog.i("token:" + token);
        mIsLogined = !TextUtils.isEmpty(token);
        mIsAuthed = WanPreferences.getInstance().getUserAuthStatus().equals("b");
        new Handler().postDelayed(this, SPLASH_SCREEN_WAITING_TIME);
    }

    @Override
    public void run() {
        if (!mIsLogined) {
            startActivity(new Intent(this, LoginActivity.class));
        } else if (!mIsAuthed) {
            startActivity(new Intent(this, AuthActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            return true;//不执行父类点击事件
        return super.onKeyDown(keyCode, event);//继续执行父类其他点击事件
    }


}
