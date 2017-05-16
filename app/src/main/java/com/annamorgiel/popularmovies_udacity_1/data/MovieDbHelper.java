package com.annamorgiel.popularmovies_udacity_1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Anna Morgiel on 04.05.2017.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitlist.db";
    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIECONTRACT_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + " (" +
                MovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_ADULT + "INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE + "DATE NOT NULL," +
                //todo: save runtime as integer?
                MovieContract.MovieEntry.COLUMN_NAME_RUNTIME + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_POPULARITY + " REAL NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_VIDEO + " INTEGER NOT NULL, " +
                MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE + " REAL NOT NULL " +
                "); ";

        db.execSQL(SQL_CREATE_MOVIECONTRACT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
