package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.util.Objects;

/**
 * This class only has three buttons to move to the other activities in the application.
 * @author Samuel Laisaar
 * @version 8.12.2020
 */

public class MainActivity extends AppCompatActivity {

    private ImageView toNotes;
    private ImageView toSleep;
    private ImageView toAlarm;

    /**
     * Hides the ActionBar and makes the background animated.
     * Three buttons for moving to different activities.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Objects.requireNonNull(getSupportActionBar()).hide();

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        toNotes = findViewById(R.id.toNotes);
        toNotes.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NotesActivity.class);
            startActivity(intent);
        });

        toSleep = findViewById(R.id.toSleep);
        toSleep.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), SleepActivity.class);
            startActivity(intent);
        });

        toAlarm = findViewById(R.id.toAlarm);
        toAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AlarmActivity.class);
            startActivity(intent);
        });
    }
}