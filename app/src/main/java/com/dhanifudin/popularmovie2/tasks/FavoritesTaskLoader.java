package com.dhanifudin.popularmovie2.tasks;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dhanifudin.popularmovie2.Constants;
import com.dhanifudin.popularmovie2.activities.MainActivity;
import com.dhanifudin.popularmovie2.adapters.MovieAdapter;
import com.dhanifudin.popularmovie2.model.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhanifudin on 8/5/17.
 */

public class FavoritesTaskLoader implements LoaderManager.LoaderCallbacks<Cursor> {
    private MainActivity activity;
    private MovieAdapter adapter;

    public FavoritesTaskLoader(MainActivity activity, MovieAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadData(LoaderManager loaderManager) {
        Loader<Cursor> loader = loaderManager.getLoader(Constants.LOADER_FAVORITE);
        if (loader == null) {
            loaderManager.initLoader(Constants.LOADER_FAVORITE, null, this);
        } else {
            loaderManager.restartLoader(Constants.LOADER_FAVORITE, null, this);
        }
        activity.showProgressIndicator();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new DatabaseTask(activity.getApplicationContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<Movie> movies = new ArrayList<>();
        if (data != null) {
            while(data.moveToNext()) {
                Movie movie = new Movie();
                movie.setId(data.getString(1));
                movie.setTitle(data.getString(2));
                movie.setOriginalTitle(data.getString(3));
                movie.setOverview(data.getString(4));
                movie.setOriginalLanguage(data.getString(5));
                movie.setReleaseDate(data.getString(6));
                movie.setVoteCount(data.getInt(7));
                movie.setVoteAverage(data.getFloat(8));
                movie.setPosterPath(data.getString(9));
                movie.setBackdropPath(data.getString(10));
                movies.add(movie);
            }
            data.close();
        }
        activity.showMovieDataView();
        adapter.setMovies(movies);
        activity.hideProgressIndicator();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
