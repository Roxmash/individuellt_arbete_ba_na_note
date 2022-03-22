package com.brandent.individuellt_arbete_note_pad.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.brandent.individuellt_arbete_note_pad.Model.NoteModel;
import com.brandent.individuellt_arbete_note_pad.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EditNote extends AppCompatActivity {

    EditText textNote;
    EditText title;

    ImageView saveButton;
    ImageView returnButton;

    String savedNoteName;
    String notes;
    String titleKey;
    String textKey;
    String toastSaved;
    String toastError;
    String removeString;

    NoteModel noteModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        toastError = getString(R.string.toast_error);
        toastSaved = getString(R.string.toast_saved);

        textNote = findViewById(R.id.et_edittext);
        title = findViewById(R.id.et_title);
        saveButton = findViewById(R.id.iv_save_note);
        returnButton = findViewById(R.id.iv_return);

        noteModelActivity = new NoteModel(this);

        removeString = ".txt";

        textKey = getIntent().getStringExtra("textKey");
        textNote.setText(textKey);

        titleKey = getIntent().getStringExtra("titleKey");

        // if the title name is longer then ".txt" witch it is if you load a title.
        // I want to remove the ".txt" witch is added when saved. This does not apply when i create
        // a new note since it's empty.

        if (titleKey.length() > removeString.length()) {
            title.setText(titleKey.substring(0, titleKey.length() - removeString.length()));
        }

        /**
         *  Creates a listener on the "save" image. When pressed it calls on the "saveNotes" method.
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedNoteName = title.getText().toString();
                notes = textNote.getText().toString();

                noteModelActivity.saveNotes(savedNoteName, notes, toastError, toastSaved);
            }
        });

        /**
         * Creates a listener on the "return" image. When pressd it exit the current activity and
         *          * the user returns to the "main_activity".
         */
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
    }
}
