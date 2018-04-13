package com.linsr.wanandroid.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.linsr.linlibrary.utils.DateUtils;
import com.linsr.linlibrary.utils.JLog;

/**
 * Description
 @author Linsr
 */

public class LocationService extends Service {
    private Handler mHandler = new Handler();


    @Override
    public void onCreate() {
        super.onCreate();
        final ScreenManager screenManager = ScreenManager.getInstance(this);
        ScreenBroadcastListener listener = new ScreenBroadcastListener(this);
        listener.registerListener(new ScreenBroadcastListener.ScreenStateListener() {
            @Override
            public void onScreenOn() {
                screenManager.finishActivity();
            }

            @Override
            public void onScreenOff() {
                screenManager.startActivity();
            }
        });
        JLog.i("===Service onCreate=== pid:%s===", android.os.Process.myPid());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                JLog.w("===", Thread.currentThread(), android.os.Process.myPid(), DateUtils.getCurrentTime());
                mHandler.postDelayed(this, 2000);
            }
        });

    }

    @Override
    public int onStartCommand(Intent intent,
                              int flags, int startId) {
//        JLog.e(this.getClass().getName(), "---------------onStartCommand -----------");
//        Notification notification = new Notification(R.mipmap.ic_launcher, "服务正在运行", System.currentTimeMillis());
//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        RemoteViews remoteView = new RemoteViews(this.getPackageName(), R.layout.activity_live);
//        remoteView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
//        remoteView.setTextViewText(R.id.text, "Hello,this message is in a custom expanded view");
//        notification.contentView = remoteView;
//        notification.contentIntent = pendingIntent;
//        startForeground(1, notification);
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
