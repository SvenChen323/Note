package com.example.android.note.database;

/**
 *
 */

public class NoteDbSchema {

    public static final class NoteTable{

        public static final String NAME = "notes";

        public static final class Cols{
            public static final String ID = "id";
            public static final String TITLE = "title";
            public static final String CONTENT = "content";
            public static final String MODIFYTIME = "modifytime";

        }

    }
}
