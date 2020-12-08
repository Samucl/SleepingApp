package com.example.androidproject;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import androidx.core.app.NotificationCompat;

/**
 * This class creates a notification. First it creates a channel for notification,
 * and then a few customizations.
 * This class is called in AlertReceiver-class, which builds the notification created here.
 * @author Juuso Lahtinen
 * @version 8.12.2020
 */

public class NotificationHelper extends ContextWrapper {

    public static final String channel_ID = "channel ID";
    public static final String channel_name = "channel name";
    private NotificationManager manager;
    public MediaPlayer alarmSoundPlayer;

    public NotificationHelper(Context base) {
        super(base);

        /**
         * API-level check
         */

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannel() {

        /**
         * this method creates a channel for notification and sets a sound to play when notification happens
         * CountDownTimer sets how long the alarm sound will play, in milliseconds. When the sound ends, it also releases the MediaPlayer.
         * sound is set using MediaPlayer and Uri to choose the soundtrack and to loop it.
         */

            NotificationChannel channel = new NotificationChannel(channel_ID, channel_name, NotificationManager.IMPORTANCE_HIGH);

            CountDownTimer timer = new CountDownTimer(30000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if (alarmSoundPlayer.isPlaying()) {
                        alarmSoundPlayer.stop();
                        alarmSoundPlayer.release();
                    }
                }
            };

            Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            alarmSoundPlayer = MediaPlayer.create(this, notificationSound);
            alarmSoundPlayer.setLooping(true);
            alarmSoundPlayer.start();
            timer.start();

            getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {

        /**
         * creates notification if there is none set yet
         * called in AlertReceiver and in createChannel().
         */

        if (manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }

    public NotificationCompat.Builder getNotification() {

        /**
         * this method sets some small customizations for notification, title, text and an icon.
         * this is also called in AlertReceiver
         */

        return new NotificationCompat.Builder(getApplicationContext(), channel_ID)
                .setContentTitle("Herätyskello")
                .setContentText("Herätys!")
                .setSmallIcon(R.drawable.ic_baseline_add_alarm_24);
    }
}