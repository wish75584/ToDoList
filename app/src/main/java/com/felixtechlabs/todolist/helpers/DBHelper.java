package com.felixtechlabs.todolist.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.felixtechlabs.todolist.models.Note;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_database";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public long saveNote(String title, String body) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Note.COLUMN_NOTE_TITLE, title);

        values.put(Note.COLUMN_NOTE_BODY, body);

        values.put(Note.COLUMN_CREATED_AT, System.currentTimeMillis());

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Note getNote(long id) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_NOTE_ID, Note.COLUMN_NOTE_TITLE, Note.COLUMN_NOTE_BODY, Note.COLUMN_CREATED_AT, Note.COLUMN_TIME_AT},
                Note.COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            int noteId = cursor.getInt(cursor.getColumnIndex(Note.COLUMN_NOTE_ID));

            Note note = new Note(noteId,
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_TITLE)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_BODY)),
                    cursor.getLong(cursor.getColumnIndex(Note.COLUMN_CREATED_AT)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_AT)));

            cursor.close();
            return  note;
        }


        return null;
    }

    public ArrayList<Note> getAllNotes() {

        ArrayList<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIME_AT + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Looping through all objects and adding it to array
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setNoteId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_NOTE_ID)));
                note.setNoteTitle(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_TITLE)));
                note.setNoteBody(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE_BODY)));
                note.setCreatedAt(cursor.getLong(cursor.getColumnIndex(Note.COLUMN_CREATED_AT)));
                note.setTimeAt(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_AT)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int updateNote(Note note) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE_ID, note.getNoteTitle());

        // updating row
        return db.update(Note.TABLE_NAME, values, Note.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
    }

    public void deleteNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Note.TABLE_NAME, Note.COLUMN_NOTE_ID + " = ?",
                new String[]{String.valueOf(note.getNoteId())});
        db.close();
    }
}
