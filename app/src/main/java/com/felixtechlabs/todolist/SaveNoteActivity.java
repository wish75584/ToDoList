package com.felixtechlabs.todolist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.felixtechlabs.todolist.helpers.DBHelper;
import com.felixtechlabs.todolist.models.Note;

public class SaveNoteActivity extends AppCompatActivity {

    static int INSERT_SUCCESS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_note);
    }

    public void submitTapped(View view) {

        DBHelper db = new DBHelper(this);

        EditText etTitle = findViewById(R.id.et_title);

        EditText etBody = findViewById(R.id.et_body);

        long id = db.saveNote(etTitle.getText().toString(), etBody.getText().toString());

        // get the newly inserted note from db
        Note note = db.getNote(id);

        if (note != null) {
            Intent intent = new Intent();
            setResult(INSERT_SUCCESS, intent);
            finish();
        }
    }
}
