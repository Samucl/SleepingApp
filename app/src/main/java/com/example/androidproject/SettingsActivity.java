package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private Button rateButton;
    private Button saveRatingButton;
    private float ratingValue;
    private Button aboutButton;
    private Button faqButton;
    private Button thanksButton;

    /**
     * Makes the background animated.
     * About button creates a toast message.
     * Rating button creates a new dialog which uses the "rating_box" layout
     * "rating_box" layout contains a button which dismisses the dialog and the RatingBars value is set to a variable called "ratingValue"
     * FaQ button creates a new dialog which uses the "about_box" layout and has a button for dismissing the dialog.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ConstraintLayout constraintLayout = findViewById(R.id.layout);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(2000);
        animationDrawable.start();
        loadData();

        aboutButton = findViewById(R.id.aboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Made by Samuel, Leo, Juuso and Miikka", Toast.LENGTH_SHORT).show();
            }
        });

        rateButton = findViewById(R.id.rateButton);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog ratingDialog = new Dialog(SettingsActivity.this);
                ratingDialog.setContentView(R.layout.rating_box);
                RatingBar ratingBar = ratingDialog.findViewById(R.id.ratingBar);
                ratingBar.setRating(ratingValue);
                ratingDialog.show();

                saveRatingButton = ratingDialog.findViewById(R.id.button1);
                saveRatingButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ratingDialog.dismiss();
                        ratingValue = ratingBar.getRating();
                        saveData();
                    }
                });
            }
        });

        faqButton = findViewById(R.id.faqButton);
        faqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog aboutDialog = new Dialog(SettingsActivity.this);
                aboutDialog.setContentView(R.layout.about_box);
                aboutDialog.show();

                thanksButton = aboutDialog.findViewById(R.id.thanksButton);
                thanksButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        aboutDialog.dismiss();
                    }
                });

            }
        });
    }

    /**
     * Here the ratingValue is saved with SharedPreferences
     */
    private void saveData(){
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("Rating", ratingValue);
        editor.apply();
    }

    /**
     * Here the ratingValue value is loaded from SharedPreferences
     */
    private void loadData(){
        SharedPreferences sp = getSharedPreferences("sp", MODE_PRIVATE);
        ratingValue = sp.getFloat("Rating", 0);
    }
}