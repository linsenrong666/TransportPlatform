package com.linsr.wanandroid.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.data.local.WanPreferences;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Description
 @author Linsr
 */
public abstract class BaseActivity extends SupportActivity {

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected String TAG;

    protected String mUserCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ApplicationEx.getInstance().addActivity(this);
        TAG = getClass().getCanonicalName();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        _init();
        init(savedInstanceState);
    }

    private void _init() {
        mUserCode = WanPreferences.getInstance().getUserCode();
    }


    @Override
    protected void onDestroy() {
        ApplicationEx.getInstance().removeActivity(this);
        super.onDestroy();
    }

}
