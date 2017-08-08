package com.dhanifudin.popularmovie2.tasks;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dhanifudin.popularmovie2.Constants;
import com.dhanifudin.popularmovie2.adapters.TrailerAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.model.Trailer;
import com.dhanifudin.popularmovie2.utilities.JsonUtils;
import com.dhanifudin.popularmovie2.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.List;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class TrailerTaskLoader implements LoaderManager.LoaderCallbacks<String> {
    private Context context;
    private TrailerAdapter adapter;

    public TrailerTaskLoader(Context context, TrailerAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    public void loadData(LoaderManager loaderManager, Movie movie) {
        URL url = NetworkUtils.buildTrailerUrl(movie.getId());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url.toString());

        Loader<String> loader = loaderManager.getLoader(Constants.LOADER_TRAILER);

        if (loader == null)
            loaderManager.initLoader(Constants.LOADER_TRAILER, bundle, this);
        else
            loaderManager.restartLoader(Constants.LOADER_TRAILER, bundle, this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new ApiTask(context, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        try {
            List<Trailer> trailers = JsonUtils.getTrailers(data);
            adapter.setTrailers(trailers);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }
}
