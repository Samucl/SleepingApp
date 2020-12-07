package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    private Button toNotes;
    private Button toSleep;
    private Button toAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toNotes = (Button) findViewById(R.id.toNotes);
        toNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NotesActivity.class);
                startActivity(intent);
            }
        });

        toSleep = (Button) findViewById(R.id.toSleep);
        toSleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SleepActivity.class);
                startActivity(intent);
            }
        });

        toAlarm = (Button) findViewById(R.id.toAlarm);
        toAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AlarmActivity.class);
                startActivity(intent);
            }
        });
    }


}