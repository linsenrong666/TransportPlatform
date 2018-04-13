package com.linsr.wanandroid.biz.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.linsr.linlibrary.gui.widgets.TitleView;
import com.linsr.wanandroid.R;
import com.linsr.wanandroid.base.BaseActivity;
import com.linsr.wanandroid.base.BaseFragment;
import com.linsr.wanandroid.biz.home.HomeFragment;
import com.linsr.wanandroid.biz.knowledge.KnowledgeFragment;
import com.linsr.wanandroid.biz.me.MeFragment;
import com.linsr.wanandroid.service.LocationService;
import com.linsr.wanandroid.utils.ToastUtils;

import butterknife.BindView;

/**
 * Description
 @author Linsr
 */

public class MainActivity extends BaseActivity {

    @BindView(R.id.main_title_view)
    TitleView mTitleView;
    @BindView(R.id.main_fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.main_nav_bar)
    BottomNavigationView mBottomNavigationView;

    private long mExitTime;
    private BaseFragment mCurrentFragment;
    private BaseFragment mHomeFragment;
    private BaseFragment mKnowledgeFragment;
    private BaseFragment mMoneyFragment;
    private BaseFragment mMeFragment;
    private Handler mHandler = new Handler();

    @Override
    protected void init(Bundle savedInstanceState) {

        mOnNavigationItemSelectedListener.onNavigationItemSelected(mBottomNavigationView.getMenu().getItem(0));
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    if (mHomeFragment == null) {
                        mHomeFragment = HomeFragment.newInstance();
                    }
                    ShowFragment(transaction, mHomeFragment);
                    return true;
                case R.id.navigation_service:
                    if (mKnowledgeFragment == null) {
                        mKnowledgeFragment = KnowledgeFragment.newInstance();
                    }
                    ShowFragment(transaction, mKnowledgeFragment);
                    return true;
                case R.id.navigation_money:
                    if (mMoneyFragment == null) {
                        mMoneyFragment = KnowledgeFragment.newInstance();
                    }
                    ShowFragment(transaction, mMoneyFragment);
                    return true;
                case R.id.navigation_my:
                    if (mMeFragment == null) {
                        mMeFragment = MeFragment.newInstance();
                    }
                    ShowFragment(transaction, mMeFragment);
                    return true;
            }
            return false;
        }

    };

    //显示fragment
    private void ShowFragment(FragmentTransaction transaction,
                              BaseFragment fragment) {
        if (mCurrentFragment == fragment) {
            return;
        }
        if (!fragment.isAdded()) {
            if (mCurrentFragment != null) {
                transaction.hide(mCurrentFragment).add(R.id.main_fragment_container, fragment).commit();
            } else {
                transaction.add(R.id.main_fragment_container, fragment).commit();
            }
        } else {
            transaction.hide(mCurrentFragment).show(fragment).commit();
        }
        mCurrentFragment = fragment;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.toast(this, R.string.exit_system);
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
