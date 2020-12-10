package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * This class has methods for detecting time picked by user (time picker) and sounding an alarm sound at picked time,
 * and button for cancelling the picked time.
 * @author Juuso Lahtinen
 * @version 8.12.2020
 */

public class AlarmActivity extends AppCompatActivity implements OnTimeSetListener {

    private TextView alarmTextView;

    /**
     * Makes the AlarmActivity background animated.
     * two onClickListeners,
     * first onClickListener is for opening time picker where user will choose time for alarm. This calls for TimePicker class where it gets correct current time.
     * second onClicklistener is for cancelling chosen time
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        alarmTextView = findViewById(R.id.textViewAlarmStatus);

        Button buttonTimePicker = findViewById(R.id.button_setAlarm);
        buttonTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new com.example.androidproject.TimePicker();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });
    }

    /**
     * Gets the time user has chosen.
     * updates a text view with the chosen time (for example 19.45) in updateTimeText method.
     * calls for startAlarm method at the chosen time
     */
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        updateTimeText(calendar);
        startAlarm(calendar);
    }

    /**
     * updates text view with the user chosen time
     */
    private void updateTimeText(Calendar calendar) {

        String time = "Alarm is set for: ";
        time += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());

        alarmTextView.setText(time);
    }

    /**
     * creates AlarmManager that allows something to happen at a given time, in this case an alarm sound.
     * At this given time, new Intent gets data from AlertReceiver class, that creates what should happen at the given time.
     * pendingIntent allows alarmIntent to happen even if app is killed or destroyed.
     * if-statement adds +1 day if user chose past time. example: time is 13.00, user chooses alarm for 12.30, it will happen the next day at 12.30.
     *
     * alarmManager then uses setExact to start the alarm: gets the time from calendar-variable, when that time comes it calls for pendingIntent
     * meaning, alarmIntent will happen.
     */
    private void startAlarm(Calendar calendar) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent  = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * cancels pendingIntent when button_cancel is pressed.
     * This cancels the alarm (alarmIntent), updates text view and toast message appears.
     */
    private void cancelAlarm() {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent  = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, alarmIntent, 0);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(AlarmActivity.this, "Alarm was cancelled", Toast.LENGTH_SHORT).show();

        alarmTextView.setText("Alarm is not set");
    }
}