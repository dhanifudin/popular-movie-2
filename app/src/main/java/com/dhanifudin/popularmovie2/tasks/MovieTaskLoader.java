package com.dhanifudin.popularmovie2.tasks;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dhanifudin.popularmovie2.Constants;
import com.dhanifudin.popularmovie2.activities.MainActivity;
import com.dhanifudin.popularmovie2.adapters.MovieAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.utilities.JsonUtils;
import com.dhanifudin.popularmovie2.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieTaskLoader implements LoaderManager.LoaderCallbacks<String> {
    private MainActivity activity;
    private MovieAdapter adapter;

    public MovieTaskLoader(MainActivity activity, MovieAdapter adapter) {
        this.activity = activity;
        this.adapter = adapter;
    }

    public void loadData(LoaderManager loaderManager, String category) {
        URL url = NetworkUtils.buildMovieUrl(category);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url.toString());

        Loader<String> loader = loaderManager.getLoader(Constants.LOADER_MOVIE);
        if (loader == null) {
            loaderManager.initLoader(Constants.LOADER_MOVIE, bundle, this);
        } else {
            loaderManager.restartLoader(Constants.LOADER_MOVIE, bundle, this);
        }
        activity.showProgressIndicator();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ApiTask(activity.getApplicationContext(), args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            ArrayList<Movie> movies = JsonUtils.getMovies(data);
            activity.showMovieDataView();
            adapter.setMovies(movies);
        } catch (JSONException e) {
            activity.showErrorMessage();
            e.printStackTrace();
        }
        activity.hideProgressIndicator();
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
