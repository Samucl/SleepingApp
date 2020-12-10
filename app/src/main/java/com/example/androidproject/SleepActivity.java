package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * This class has methods for starting and stopping a chronometer, also for saving and loading an ArrayList
 * A time called "offset" is calculated with SystemClock (When the start and stop methods are called) and is saved to the ArrayList
 * A boolean run is used to determine when the timer is running.
 * Both the time and boolean are saved when the application is closed.
 * @author Samuel Laisaar
 * @version 8.12.2020
 */

public class SleepActivity extends AppCompatActivity {

    private Chronometer timer;
    private boolean run = false;
    private long offset;
    private Button sleepNow;
    private Button wakeNow;
    private ArrayList<String> addArray;
    private ListView showSleep;
    private Button sounds;

    /**
     * A button for moving to the SoundsActivity.
     * Long click on the "showSleep" ListView item shows an AlertDialog where you can delete the item.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);

        timer = findViewById(R.id.timer);
        showSleep = findViewById(R.id.listView);
        sleepNow = findViewById(R.id.sleepButton);
        wakeNow = findViewById(R.id.wakeButton);

        loadArray();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SleepActivity.this, R.layout.listview_style, addArray);
        showSleep.setAdapter(adapter);

        sounds = findViewById(R.id.toSounds);
        sounds.setOnClickListener(v -> startActivity(new Intent(SleepActivity.this, SoundsActivity.class)));

        showSleep.setOnItemLongClickListener((parent, view, position, id) -> {
            final int item = position;
            new AlertDialog.Builder(SleepActivity.this).setTitle("Do you want to delete this item?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        addArray.remove(item);
                        showSleep.invalidateViews();
                        saveArray();
                    })
                    .setNegativeButton("No", null).show();
            return true;
        });
    }
    /**
     * Changes the visibility of buttons.
     * Starts the timer and sets a base time from SystemClock
     */
    public void startTimer(View v){
        if(!run){
            sounds.setVisibility(View.VISIBLE);
            wakeNow.setVisibility(View.VISIBLE);
            sleepNow.setVisibility(View.INVISIBLE);
            timer.setBase(SystemClock.elapsedRealtime());
            timer.start();
            run = true;
        }
    }

    /**
     * Stops the timer.
     * Changes the visibility of buttons.
     * Determines the current day, calculates the slept time and displays date and time in a readable format in the ListView.
     */
    public void stopTimer(View v){
        if(run){
            timer.stop();
            sounds.setVisibility(View.INVISIBLE);
            sleepNow.setVisibility(View.VISIBLE);
            wakeNow.setVisibility(View.INVISIBLE);
            offset = SystemClock.elapsedRealtime() - timer.getBase();

            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH); month++;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            long seconds = offset/1000;
            long minutes = seconds/60;
            long hours = minutes/60;

            addArray.add(day + "." + month + "." + year + "\n" + "Slept " + hours % 24 + " hours " + minutes % 60 + " minutes and " + seconds % 60 + " seconds");
            showSleep.invalidateViews();

            timer.setBase(SystemClock.elapsedRealtime());
            offset = 0;
            run = false;
            saveArray();
        }
    }

    /**
     * When onStop method is called the base time and the state of "run" are saved in SharedPreferences.
     * Prevents the possibility for user exiting the application in which case the base time and "run" are lost.
     */
    protected void onStop(){
        super.onStop();
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong("milliseconds", timer.getBase());
        editor.putBoolean("running", run);
        editor.apply();
    }
    /**
     * When onStart method is called the base time and the state of "run" are loaded from SharedPreferences
     * Also the state of run determines which buttons are visible.
     */
    protected void onStart(){
        super.onStart();
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        timer.setBase(sp.getLong("milliseconds", 0));
        run = sp.getBoolean("running", false);

        if(run){
            offset = SystemClock.elapsedRealtime() - timer.getBase();
            wakeNow.setVisibility(View.VISIBLE);
            sleepNow.setVisibility(View.INVISIBLE);
            sounds.setVisibility(View.VISIBLE);
        }
        else{
            wakeNow.setVisibility(View.INVISIBLE);
            sleepNow.setVisibility(View.VISIBLE);
            sounds.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * Converts the ArrayList to Json and saves the Json string to SharedPreferences
     */
    private void saveArray(){
        SharedPreferences sp = getSharedPreferences("ArrayList", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(addArray);
        editor.putString("task arraylist", json);
        editor.apply();
    }
    /**
     * Loads the ArrayList from SharedPreferences and converts it from Json
     */
    private void loadArray(){
        SharedPreferences sp = getSharedPreferences("ArrayList", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("task arraylist", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        addArray = gson.fromJson(json, type);

        if(addArray == null){
            addArray = new ArrayList<>();
        }
    }
}
