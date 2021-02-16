package com.example.mynoteapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.HashSet;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        EditText editText = (EditText)findViewById(R.id.editText);
        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID",-1); // default value -1 in case of intent error

        if(noteID != -1)
        {
            editText.setText(MainActivity.notes.get(noteID));
        }

        else
        {
            MainActivity.notes.add(""); //  initially first note would be empty
            noteID = MainActivity.notes.size()-1;
            MainActivity.arrayAdapter.notifyDataSetChanged();

        }

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                MainActivity.notes.set(noteID,String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();

                // storing data permanently to our app by using sharedPreferences

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.tanay.thunderbird.notes", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(MainActivity.notes);
                sharedPreferences.edit().putStringSet("notes",set).apply();


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}