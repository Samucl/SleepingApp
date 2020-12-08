package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.HashMap;

/**
 * This class has methods for data listening (by DatePicker), saving and loading data from SharedPreferences.
 * Buttons display, and save the date selected by DatePicker.
 * Notes are handled with HashMap which are displayed with TextView.
 * @author Samuel Laisaar
 * @version 8.12.2020
 */

public class NotesActivity extends AppCompatActivity {

    private DatePickerDialog.OnDateSetListener DataSetListener;
    private EditText NoteText;
    private TextView DisplayNote;
    private Button SaveNote;
    private Button DateButton;
    private Button DeleteNote;
    private HashMap<String, String> notes;

    /**
     * First hides all other buttons except DateButton.
     * Three onClickListeners, and one DataListener.
     * First onClickListener is for opening DatePicker and its data is set from the DateListener.
     * The data is set to a string variable called "date" and is used as a key in HashMap.
     * HashMaps value is the string "note" that is set when the second onClickListener is called.
     * Second onClickListener is for displaying the note in TextView and saving it to SharedPreferences.
     * Third onClickListener is for deleting the note from HashMap.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        loadData();

        NoteText = findViewById(R.id.noteText);
        DisplayNote = findViewById(R.id.displayNote);
        SaveNote = findViewById(R.id.saveNote);
        DateButton = findViewById(R.id.displayDateButton);
        DeleteNote = findViewById(R.id.deleteNote);

        DeleteNote.setVisibility(View.INVISIBLE);
        NoteText.setVisibility(View.INVISIBLE);
        SaveNote.setVisibility(View.INVISIBLE);

        DateButton.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(NotesActivity.this, android.R.style.Theme_Holo_Light_Dialog, DataSetListener, year,month,day);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();
        });

        DataSetListener = (view, year, month, day) -> {
            month++;
            String date = day + "." + month + "." + year;
            DateButton.setText(date);
            DisplayNote.setText(notes.get(date));

            DeleteNote.setVisibility(View.VISIBLE);
            NoteText.setVisibility(View.VISIBLE);
            SaveNote.setVisibility(View.VISIBLE);

            SaveNote.setOnClickListener(v -> {
                if(!NoteText.getText().toString().isEmpty()){
                    notes.put(date, NoteText.getText().toString());
                }
                DisplayNote.setText(notes.get(date));
                NoteText.setText("");
                saveData();
            });

            DeleteNote.setOnClickListener(v -> {
                notes.put(date, "");
                DisplayNote.setText(notes.get(date));
                saveData();
            });
        };
    }

    /**
     * Loads the HashMap from SharedPreferences and converts it from Json
     */
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        notes = gson.fromJson(json, type);

        if(notes == null){
            notes = new HashMap<>();
        }
    }

    /**
     * Converts the HashMap to Json and saves the Json string to SharedPreferences
     */
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        editor.putString("notes list", json);
        editor.apply();
    }
}
