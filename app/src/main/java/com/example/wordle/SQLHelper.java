package com.example.wordle;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/** Helper to the database, manages versions and creation */
public class SQLHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "player.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    public static final String TABLE = "score";

    // Columns
    public static final String GUESSES = "guesses";

    public SQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + TABLE + "( "
                + BaseColumns._ID + " integer primary key autoincrement, "
                + GUESSES + " integer);";
        Log.d("Data", "onCreate: " + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion)
            return;

        String sql = null;
        if (oldVersion == 1)
            sql = "alter table " + TABLE + " add note text;";
        if (oldVersion == 2)
            sql = "";

        Log.d("Data", "onUpgrade	: " + sql);
        if (sql != null)
            db.execSQL(sql);
    }

}
