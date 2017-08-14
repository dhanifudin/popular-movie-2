package com.dhanifudin.popularmovie2.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanifudin.popularmovie2.Constants;
import com.dhanifudin.popularmovie2.R;
import com.dhanifudin.popularmovie2.adapters.MovieAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.tasks.FavoritesTaskLoader;
import com.dhanifudin.popularmovie2.tasks.MovieTaskLoader;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView moviesView;
    private MovieAdapter movieAdapter;

    private TextView errorMessageText;
    private ProgressBar loadingProgress;

    private String category = Constants.CATEGORY_POPULAR;
    private MovieTaskLoader movieTaskLoader;
    private FavoritesTaskLoader favoritesTaskLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessageText = (TextView) findViewById(R.id.text_error);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        moviesView = (RecyclerView) findViewById(R.id.movies_view);

        int orientation = getResources().getConfiguration().orientation;
        int column = (orientation == Configuration.ORIENTATION_PORTRAIT) ? 2 : 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, column);
        moviesView.setLayoutManager(layoutManager);
        moviesView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        moviesView.setAdapter(movieAdapter);

        movieTaskLoader = new MovieTaskLoader(this, movieAdapter);
        favoritesTaskLoader = new FavoritesTaskLoader(this, movieAdapter);

        if (savedInstanceState == null) {
            category = Constants.CATEGORY_POPULAR;
            requestMovies(category);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(TAG, "onSaveInstanceState");
        outState.putString(Constants.CATEGORY, category);
        outState.putParcelableArrayList(Constants.MOVIES, movieAdapter.getMovies());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(TAG, "onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            ArrayList<Movie> movies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
            movieAdapter.setMovies(movies);
            category = savedInstanceState.getString(Constants.CATEGORY);
        } else {
            Log.i(TAG, "Requesting movies");
            requestMovies(category);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_popular) {
            category = Constants.CATEGORY_POPULAR;
        } else if (id == R.id.action_top_rated) {
            category = Constants.CATEGORY_TOP_RATED;
        } else if (id == R.id.action_favorite) {
            category = Constants.CATEGORY_FAVORITE;
        }
        requestMovies(category);
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void requestMovies(String category) {
        if (isOnline()) {
            Toast.makeText(this, "Requesting " + category + " movies.", Toast.LENGTH_LONG)
                    .show();
            if (!Constants.CATEGORY_FAVORITE.equals(category)) {
                movieTaskLoader.loadData(getSupportLoaderManager(), category);
            } else {
                favoritesTaskLoader.loadData(getSupportLoaderManager());
            }
        } else {
            Toast.makeText(this, getString(R.string.connection_error), Toast.LENGTH_LONG)
                    .show();
        }
    }

    public void showProgressIndicator() {
        loadingProgress.setVisibility(View.VISIBLE);
    }

    public void hideProgressIndicator() {
        loadingProgress.setVisibility(View.INVISIBLE);
    }

    public void showMovieDataView() {
        errorMessageText.setVisibility(View.INVISIBLE);
        moviesView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage() {
        moviesView.setVisibility(View.INVISIBLE);
        errorMessageText.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Movie movie) {
        Intent detailIntent = new Intent(this, MovieDetailActivity.class);
        detailIntent.putExtra(Constants.MOVIE, movie);
        startActivity(detailIntent);
    }
}
