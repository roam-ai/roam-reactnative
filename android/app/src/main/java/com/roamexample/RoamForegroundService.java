package com.roamexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.RequiresApi;

import com.roam.reactnative.RNRoamReceiver;
import com.roam.sdk.service.RoamReceiver;

public class RoamForegroundService extends Service {

    private RNRoamReceiver mLocationReceiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(NotificationHelper.NOTIFICATION_ID, NotificationHelper.showNotification(this));
        }
        register();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        NotificationHelper.cancelNotification(this);
        unRegister();
        super.onDestroy();
    }

    private void register() {
        mLocationReceiver = new RNRoamReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.roam.android.RECEIVED");
        registerReceiver(mLocationReceiver, intentFilter);
    }

    private void unRegister() {
        if (mLocationReceiver != null) {
            unregisterReceiver(mLocationReceiver);
        }
    }

}


class NotificationHelper {
    public static final int NOTIFICATION_ID = 102;
    private static final int PENDING_INTENT_REQUEST_CODE = 103;
    private static final String ANDROID_CHANNEL_ID = "com.module.react";
    private static final String ANDROID_CHANNEL_NAME = "motion";

    @RequiresApi(api = Build.VERSION_CODES.O)
    private static Notification.Builder getAndroidChannelNotification(Context context) {
        String contentTitle = "App is running";
        String contentText = "Click here to open the app";
        return new Notification.Builder(context, ANDROID_CHANNEL_ID)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setStyle(new Notification.BigTextStyle().bigText(contentText))
                .setAutoCancel(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel(ANDROID_CHANNEL_ID, ANDROID_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW);
        channel.enableLights(false);
        channel.enableVibration(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        notificationManager.createNotificationChannel(channel);
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder nb = getAndroidChannelNotification(context);
        nb.setContentIntent(pendingIntent);
        return nb.build();
    }

    public static void cancelNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(NOTIFICATION_ID);
        }
    }
}
