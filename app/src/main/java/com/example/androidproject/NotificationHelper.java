package com.example.androidproject;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String channel_ID = "channel ID";
    public static final String channel_name = "channel name";

    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {
            NotificationChannel channel = new NotificationChannel(channel_ID, channel_name, NotificationManager.IMPORTANCE_HIGH);

            getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return manager;
    }

    public NotificationCompat.Builder getNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channel_ID)
                .setContentTitle("Herätyskello")
                .setContentText("Toimii")
                .setSmallIcon(R.drawable.ic_baseline_add_alarm_24);
    }
}
