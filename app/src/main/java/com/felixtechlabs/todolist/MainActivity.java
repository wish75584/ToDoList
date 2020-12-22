package com.felixtechlabs.todolist;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.felixtechlabs.todolist.adapters.NotesAdapter;
import com.felixtechlabs.todolist.helpers.DBHelper;
import com.felixtechlabs.todolist.models.Note;

public class MainActivity extends AppCompatActivity {

    NotesAdapter notesAdapter;

    DBHelper dbHelper;

    static int INSERT_SUCCESS = 0;

    static int INSERT_FAIL = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_notes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);

        notesAdapter = new NotesAdapter(dbHelper);

        recyclerView.setAdapter(notesAdapter);
    }

    public void addTapped(View view) {

        Intent intent = new Intent(MainActivity.this, SaveNoteActivity.class);

        startActivityForResult(intent,  INSERT_SUCCESS);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INSERT_SUCCESS) {
            notesAdapter.notifyDataSetChanged();
        }
    }


}
