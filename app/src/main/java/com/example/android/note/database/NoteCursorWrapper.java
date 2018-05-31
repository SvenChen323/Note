package com.example.android.note.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.android.note.Note;


/**
 *
 */

public class NoteCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        int id = getInt(getColumnIndex(NoteDbSchema.NoteTable.Cols.ID));
        String title = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.TITLE));
        String content = getString(getColumnIndex(NoteDbSchema.NoteTable.Cols.CONTENT));
        long modifytime = getLong(getColumnIndex(NoteDbSchema.NoteTable.Cols.MODIFYTIME));


        Note book = new Note(id,title,content,modifytime);

        return book;
    }


}
