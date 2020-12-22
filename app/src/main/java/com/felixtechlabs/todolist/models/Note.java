package com.felixtechlabs.todolist.models;

public class Note {

    public static final String TABLE_NAME = "notes";

    public static final String COLUMN_NOTE_ID = "noteId";
    public static final String COLUMN_NOTE_TITLE = "noteTitle";
    public static final String COLUMN_NOTE_BODY = "noteBody";
    public static final String COLUMN_CREATED_AT = "createdAt";
    public static final String COLUMN_TIME_AT = "timeAt";

    private int noteId;
    private String noteTitle;
    private String noteBody;
    private long createdAt;
    private String timeAt;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_NOTE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NOTE_TITLE + " TEXT,"
                    + COLUMN_NOTE_BODY + " TEXT,"
                    + COLUMN_CREATED_AT + " LONG,"
                    + COLUMN_TIME_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public Note() {
    }

    public Note(int noteId, String noteTitle, String noteBody, long createdAt, String timeAt) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteBody = noteBody;
        this.createdAt = createdAt;
        this.timeAt = timeAt;
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public String getTimeAt() {
        return timeAt;
    }

    public void setTimeAt(String timeAt) {
        this.timeAt = timeAt;
    }
}
