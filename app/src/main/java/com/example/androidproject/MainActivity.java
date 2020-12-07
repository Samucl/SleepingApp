package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private ImageView toNotes;
    private ImageView toSleep;
    private ImageView toAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hides ActionBar
        getSupportActionBar().hide();

        //Makes the MainActivity background animated
        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();

        //Adds onClick method to notes button
        toNotes = (ImageView) findViewById(R.id.toNotes);
        toNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotesActivity.class);
                startActivity(intent);
            }
        });

        //Adds onClick method to sleep button
        toSleep = (ImageView) findViewById(R.id.toSleep);
        toSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SleepActivity.class);
                startActivity(intent);
            }
        });

        //Adds onClick method to alarm button
        toAlarm = (ImageView) findViewById(R.id.toAlarm);
        toAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });
    }


}