package com.dhanifudin.popularmovie2.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dhanifudin.popularmovie2.data.MovieContract.MovieEntry;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieProvider extends ContentProvider {

    public static final int MOVIES = 100;
    public static final int MOVIES_ID = 101;

    private static final UriMatcher matcher = buildUriMatcher();
    private MovieDbHelper openHelper;

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIES);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIES_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        openHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor = null;
        switch (matcher.match(uri)) {
            case MOVIES_ID: {
                cursor = openHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case MOVIES: {
                cursor = openHelper.getReadableDatabase().query(
                        MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();

        int match = matcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                long id = db.insert(MovieEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(MovieEntry.CONTENT_URI, id);
                } else {
                    throw new SQLException("Failed to insert row into " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = openHelper.getWritableDatabase();
        int match = matcher.match(uri);
        int movieDeleted;
        switch (match) {
            case MOVIES_ID:
                String id = uri.getPathSegments().get(1);
                String where = String.format("%s=?", MovieEntry.COLUMN_MOVIE_ID);
                movieDeleted = db.delete(MovieEntry.TABLE_NAME, where, new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (movieDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);;
        }
        return movieDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
