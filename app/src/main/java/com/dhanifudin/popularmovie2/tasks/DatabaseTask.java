package com.dhanifudin.popularmovie2.tasks;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import com.dhanifudin.popularmovie2.data.MovieContract.MovieEntry;

/**
 * Created by dhanifudin on 8/5/17.
 */

public class DatabaseTask extends AsyncTaskLoader<Cursor> {

    private Cursor data;

    public DatabaseTask(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    public Cursor loadInBackground() {
        Cursor cursor = null;
        try {
            cursor = getContext()
                .getContentResolver()
                .query(MovieEntry.CONTENT_URI, null, null, null, MovieEntry._ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

    @Override
    public void deliverResult(Cursor data) {
        this.data = data;
        super.deliverResult(data);
    }
}
