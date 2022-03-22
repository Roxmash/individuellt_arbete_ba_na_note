package com.brandent.individuellt_arbete_note_pad.Model;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.brandent.individuellt_arbete_note_pad.Controller.EditNote;
import com.brandent.individuellt_arbete_note_pad.R;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class NoteModel {

    AppCompatActivity activity;
    File folder;

    /**
     * A method that checks if there is a folder to save out note in. in it doesn't exist
     * it create it with a title: "saved notes".
     */
    public NoteModel(AppCompatActivity activity){
        this.activity = activity;

        try {
            folder = new File(activity.getFilesDir(), "Saved notes");
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception e){
        }
    }

    /**
     * A method that saves the data. its terms are a title and a text. the user gets a message when the
     * terms are followed or not. The name of the saved file is the titleinput and it writes the filecontent
     * from the textinput.
     * @param savedNoteName note title.
     * @param notes note text.
     * @param toastError save error text.
     * @param toastSaved save sucess text
     */
    public void saveNotes(String savedNoteName, String notes, String toastError, String toastSaved) {

        if (TextUtils.isEmpty(savedNoteName) || TextUtils.isEmpty(notes)) {
            Toast.makeText(activity, toastError , Toast.LENGTH_LONG).show();
        }
        else {
            try {
                File file = new File(folder, savedNoteName + ".txt");
                FileWriter writer = new FileWriter(file);
                writer.append(notes);
                writer.close();
            } catch (IOException e) {
            }
            Toast.makeText(activity, toastSaved, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * A Method that loads all the filenames from the folder "saved notes"
     * * into the the string "noteTitles".
     * @return noteTitles
     */
    public ArrayList <String> loadTitles(){
        ArrayList <String> noteTitles = new ArrayList<>();
        try{
            File [] files = folder.listFiles();

            for (File currentfile: files) {

                noteTitles.add(currentfile.getName());
            }
        }catch (Exception e){
        }
        return noteTitles;
    }
    /**
     * @param title aka. file name.
     * A method that loads the text from the selected title with the help of a scanner.
     * * All content that is scanned is added on a stringbuilder.
     * @return noteTextBuilder
     */
    public String loadText(String title){
        StringBuilder noteTextBuilder = new StringBuilder();
        try{
            File file = new File(folder, title);
            Scanner textScanner = new Scanner(file);

            while (textScanner.hasNext()){
                noteTextBuilder.append(textScanner.nextLine());
            }
        }catch (Exception e){
        }
        return noteTextBuilder.toString();
    }
    /**
     * @param title aka. file name.
     * A method that checks if the title matches any file with the name of the selected title.
     * if found it deletes the file.
     */
    public void deleteNote (String title){
        File file = new File(folder, title);
        if( file.exists()){
            file.delete();
        }
    }
}

