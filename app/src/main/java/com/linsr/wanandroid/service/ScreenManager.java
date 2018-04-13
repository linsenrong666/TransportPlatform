package com.linsr.wanandroid.service;

import android.app.Activity;
import android.content.Context;

import com.linsr.linlibrary.utils.JLog;

import java.lang.ref.WeakReference;

/**
 * Description
 @author Linsr
 */

public class ScreenManager {

    private Context mContext;

    private WeakReference<Activity> mActivityWref;

    public static ScreenManager gDefualt;

    public static ScreenManager getInstance(Context pContext) {
        if (gDefualt == null) {
            gDefualt = new ScreenManager(pContext.getApplicationContext());
        }
        return gDefualt;
    }

    private ScreenManager(Context pContext) {
        this.mContext = pContext;
    }

    public void setActivity(Activity pActivity) {
        mActivityWref = new WeakReference<>(pActivity);
    }

    public void startActivity() {
        JLog.i("===", "startActivity");
        LiveActivity.actionToLiveActivity(mContext);
    }

    public void finishActivity() {
        //结束掉LiveActivity
        if (mActivityWref != null) {
            JLog.i("===finishActivity", mActivityWref.get());
            Activity activity = mActivityWref.get();
            if (activity != null) {
                activity.finish();
            }
        }
        JLog.i("===finishActivity", mActivityWref);
    }

}

