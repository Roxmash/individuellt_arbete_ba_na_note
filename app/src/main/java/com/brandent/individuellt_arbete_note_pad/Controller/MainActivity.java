package com.brandent.individuellt_arbete_note_pad.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.brandent.individuellt_arbete_note_pad.Model.NoteModel;
import com.brandent.individuellt_arbete_note_pad.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView savedNoteList;

    ImageView newNote;
    ImageView info;
    ImageView close;

    String yesPopup;
    String noPopup;

    Dialog popup;

    ArrayAdapter arrayAdapterNotes;
    ArrayList<String> arrayListNotes = new ArrayList<>();

    NoteModel noteModelActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        savedNoteList = findViewById(R.id.list_view_saved_notes);
        newNote = findViewById(R.id.iv_new_note);
        info = findViewById(R.id.iv_info);

        yesPopup = getString(R.string.popup_yes_msg);
        noPopup = getString(R.string.popup_No_msg);

        popup = new Dialog(this);

        arrayAdapterNotes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListNotes);

        noteModelActivity = new NoteModel(this);

        //on create, calls on the "updatelistView" method.
        updateListView();

        /**
         * Creates a listener on the "info" image. when clicked, it open the "popup_window"
         * and display its content.
         */
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.setContentView(R.layout.popup_window);
                close = popup.findViewById(R.id.iv_popup_close_icon);

                /**
                 * Creates a listener on the "exit" image. when clicked, it closes "popup_window".
                 */
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popup.dismiss();
                    }
                });
                popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popup.show();
            }
        });

        /**
         * Creates a listener on the "new note" image. It transfer us to the "EditNote" activity.
         */
        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addNewNote = new Intent(MainActivity.this, EditNote.class);

                addNewNote.putExtra("titleKey", "");
                addNewNote.putExtra("textKey", "");

                startActivity(addNewNote);
            }
        });

        /**
         * Creates a listener on the listview's items. It transfer us to the "EditNote activity.
         * With the content that's been saved within the title, such as title and text.
         */
        savedNoteList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent openNote = new Intent(MainActivity.this, EditNote.class);

                TextView textView = (TextView) view;

                openNote.putExtra("titleKey", textView.getText().toString());
                openNote.putExtra("textKey",noteModelActivity.loadText(textView.getText().toString()));

                startActivity(openNote);
            }
        });

        /**
         * Creates a listener of a "longclick" on the listview's items. if you press and hold an item,
         * a message pops up and ask the user if you wish to delete the note or not.
         */
        savedNoteList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                TextView title = (TextView) view;

                new AlertDialog.Builder(MainActivity.this).setTitle(getString(R.string.popup_delete_msg))
                        .setPositiveButton(yesPopup, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    MainActivity.this.noteModelActivity.deleteNote(title.getText().toString());
                                    updateListView();
                            }
                        })
                        .setNegativeButton(noPopup, null)
                        .show();
                return true;
            }
        });
    }
    //on resume, calls on the "updatelistView" method.
    @Override
    protected void onResume() {
        super.onResume();
        updateListView();
    }

    /**
     * We update the arraylist with the content from the "loadTitles" method.
     * Then we transfer the content into the new adapter.
     * Then we update the listview with the information from the new adapter.
     */
    public void updateListView(){

        arrayListNotes = noteModelActivity.loadTitles();

        arrayAdapterNotes = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayListNotes);
        savedNoteList.setAdapter(arrayAdapterNotes);
    }
}