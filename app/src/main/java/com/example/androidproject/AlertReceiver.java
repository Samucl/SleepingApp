package com.example.androidproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;

/**
 * This class builds notification for the alarm, using NotificationHelper class that creates the notification
 * this class is called in AlarmActivity when the alarm happens (alarmIntent).
 */

public class AlertReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder notificationBuilder = notificationHelper.getNotification();
        notificationHelper.getManager().notify(1, notificationBuilder.build());
    }
}