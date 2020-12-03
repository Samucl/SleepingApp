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

public class NotesActivity extends AppCompatActivity {
    private static final String TAG = "NotesActivity";

    private DatePickerDialog.OnDateSetListener DataSetListener;
    private EditText NoteText;
    private TextView DisplayNote;
    private Button SaveNote;
    private Button DateButton;
    private Button DeleteNote;
    private HashMap<String, String> notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        loadData();

        NoteText = (EditText) findViewById(R.id.noteText);
        DisplayNote = (TextView) findViewById(R.id.displayNote);
        SaveNote = (Button) findViewById(R.id.saveNote);
        DateButton = (Button) findViewById(R.id.displayDateButton);
        DeleteNote = (Button) findViewById(R.id.deleteNote);

        DateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(NotesActivity.this, android.R.style.Theme_Holo_Light_Dialog, DataSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        DataSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month++;
                String date = day + "." + month + "." + year;
                DateButton.setText(date);
                DisplayNote.setText(notes.get(date));

                SaveNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!NoteText.getText().toString().isEmpty()){
                            notes.put(date, NoteText.getText().toString());
                        }
                        DisplayNote.setText(notes.get(date));
                        NoteText.setText("");
                        saveData();
                    }
                });

                DeleteNote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        notes.put(date, "");
                        DisplayNote.setText(notes.get(date));
                        saveData();
                    }
                });
            }
        };
    }

    public void loadData(){
        //Lataa HashMapin tiedot sharedPrefrencestä
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type = new TypeToken<HashMap<String, String>>() {}.getType();
        notes = gson.fromJson(json, type);

        if(notes == null){
            notes = new HashMap<String, String>();
        }
    }

    public void saveData(){
        //Tallentaa HashMapin tiedot sharedPrefrenceen
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(notes);
        editor.putString("notes list", json);
        editor.apply();
    }
}
