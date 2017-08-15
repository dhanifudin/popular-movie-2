package com.dhanifudin.popularmovie2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dhanifudin.popularmovie2.data.MovieContract.MovieEntry;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "movie.db";

    private static final int DATABASE_VERSION = 1;

    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE =
            "CREATE TABLE " +
                MovieEntry.TABLE_NAME + " (" +
                MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                MovieEntry.COLUMN_MOVIE_TITLE + " TEXT," +
                MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE + " TEXT, " +
                MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT, " +
                MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE + " TEXT, " +
                MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT, " +
                MovieEntry.COLUMN_MOVIE_VOTE_COUNT + " INTEGER, " +
                MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE + " REAL, " +
                MovieEntry.COLUMN_MOVIE_POSTER + " TEXT," +
                MovieEntry.COLUMN_MOVIE_BACKDROP + " TEXT," +
                "UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
