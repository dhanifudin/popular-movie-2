package com.dhanifudin.popularmovie2;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanifudin.popularmovie2.adapters.MovieAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.tasks.FavoritesTaskLoader;
import com.dhanifudin.popularmovie2.tasks.MovieTaskLoader;
import com.dhanifudin.popularmovie2.utilities.JsonUtils;
import com.dhanifudin.popularmovie2.tasks.ApiTask;
import com.dhanifudin.popularmovie2.utilities.NetworkUtils;

import org.json.JSONException;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView moviesView;
    private MovieAdapter movieAdapter;

    private TextView errorMessageText;
    private ProgressBar loadingProgress;

    private ArrayList<Movie> movies;

    private String category;
    private MovieTaskLoader movieTaskLoader;
    private FavoritesTaskLoader favoritesTaskLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        errorMessageText = (TextView) findViewById(R.id.text_error);
        loadingProgress = (ProgressBar) findViewById(R.id.loading_progress);
        moviesView = (RecyclerView) findViewById(R.id.movies_view);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false);
        moviesView.setLayoutManager(layoutManager);
        moviesView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        movieAdapter.setMovies(movies);
        moviesView.setAdapter(movieAdapter);

        movieTaskLoader = new MovieTaskLoader(this, movieAdapter);
        favoritesTaskLoader = new FavoritesTaskLoader(this, movieAdapter);

        if (savedInstanceState == null) {
            category = Constants.CATEGORY_POPULAR;
            requestMovies();
        } else {
            category = savedInstanceState.getString(Constants.CATEGORY);
            ArrayList<Movie> parcelableMovies = savedInstanceState.getParcelableArrayList(Constants.MOVIES);
            if (parcelableMovies != null) {
                movies = parcelableMovies;
                movieAdapter.setMovies(movies);
            } else {
                requestMovies();
            }
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
        outState.putString(Constants.CATEGORY, category);
        outState.putParcelableArrayList(Constants.MOVIES, movies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_popular:
                category = Constants.CATEGORY_POPULAR;
                requestMovies();
                return true;
            case R.id.action_top_rated:
                category = Constants.CATEGORY_TOP_RATED;
                requestMovies();
                return true;
            case R.id.action_favorite:
                favoritesTaskLoader.loadData(getSupportLoaderManager());
                return true;
            case R.id.action_refresh:
                requestMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnectedOrConnecting();
    }

    private void requestMovies() {
        if (isOnline()) {
            Toast.makeText(this, "Requesting " + category + " movies.", Toast.LENGTH_LONG)
                    .show();
            movieTaskLoader.loadData(getSupportLoaderManager(), category);
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
