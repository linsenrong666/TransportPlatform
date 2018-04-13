package com.linsr.wanandroid.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.linsr.linlibrary.net.NetReqManager;
import com.linsr.linlibrary.utils.JLog;
import com.linsr.wanandroid.BuildConfig;
import com.linsr.wanandroid.data.local.WanPreferences;
import com.linsr.wanandroid.data.remote.TransportStore;

import java.util.ArrayList;
import java.util.List;

import me.yokeyword.fragmentation.Fragmentation;

/**
 * Description
 @author Linsr
 */

public class ApplicationEx extends Application {

    private static ApplicationEx mInstance;
    public List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        JLog.i("===Application onCreate=== pid:%s===", android.os.Process.myPid());
        mInstance = this;
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(BuildConfig.DEBUG)
                .install();
        init();

    }

    private void init() {
        NetReqManager netReqManager = NetReqManager.getInstance();
        netReqManager.init(TransportStore.HOST, BuildConfig.DEBUG);

        WanPreferences.getInstance().init(this);
    }

    public static ApplicationEx getInstance() {
        return mInstance;
    }

    /**
     * 判断是不是UI主进程，因为有些东西只能在UI主进程初始化
     */
    public static boolean isAppMainProcess() {
        try {
            int pid = android.os.Process.myPid();
            String process = getAppNameByPID(ApplicationEx.getInstance(), pid);
            if (TextUtils.isEmpty(process)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 根据Pid得到进程名
     */
    public static String getAppNameByPID(Context context, int pid) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                return processInfo.processName;
            }
        }
        return "";
    }

    /**
     * 往Activity列表中添加Activity
     */
    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }
    public void removeActivity(Activity activity) {
        mActivities.remove(activity);
    }
}
