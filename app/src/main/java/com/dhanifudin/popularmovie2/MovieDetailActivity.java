package com.dhanifudin.popularmovie2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dhanifudin.popularmovie2.adapters.ReviewAdapter;
import com.dhanifudin.popularmovie2.adapters.TrailerAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.model.Review;
import com.dhanifudin.popularmovie2.model.Trailer;
import com.dhanifudin.popularmovie2.utilities.JsonUtils;
import com.dhanifudin.popularmovie2.utilities.MovieTaskLoader;
import com.dhanifudin.popularmovie2.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.net.URL;

import static com.dhanifudin.popularmovie2.Constants.MOVIE;

public class MovieDetailActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>,
        TrailerAdapter.TrailerAdapterOnClickHandler {

    private Movie movie;
    private Trailer[] trailers;
    private Review[] reviews;

    private ImageView backdropImage;
    private ImageView posterImage;
    private TextView titleText;
    private TextView originalTitleText;
    private TextView releaseText;
    private RatingBar movieRating;
    private TextView informationText;
    private TextView overviewText;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE)) {
            Bundle bundle = getIntent().getExtras();
            movie = bundle.getParcelable(MOVIE);
        } else {
            movie = savedInstanceState.getParcelable(MOVIE);
        }

        backdropImage = (ImageView) findViewById(R.id.image_backdrop);
        titleText = (TextView) findViewById(R.id.text_title);
        posterImage = (ImageView)  findViewById(R.id.image_poster);
        originalTitleText = (TextView) findViewById(R.id.text_original_title);
        releaseText = (TextView) findViewById(R.id.text_release);
        movieRating = (RatingBar) findViewById(R.id.rating_movie);
        informationText = (TextView) findViewById(R.id.text_information);
        overviewText = (TextView) findViewById(R.id.text_overview);
        RecyclerView trailerView = (RecyclerView) findViewById(R.id.trailer_list);
        RecyclerView reviewView = (RecyclerView) findViewById(R.id.review_list);

        RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailerView.setLayoutManager(trailerLayoutManager);
        trailerView.setHasFixedSize(true);
        trailerAdapter = new TrailerAdapter(this);
        trailerAdapter.setTrailers(trailers);
        trailerView.setAdapter(trailerAdapter);

        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewView.setLayoutManager(reviewLayoutManager);
        reviewView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter();
        reviewAdapter.setReviews(reviews);;
        reviewView.setAdapter(reviewAdapter);

        displayData();
        loadTrailerData();
        loadReviewData();

        Button favoriteButton = (Button) findViewById(R.id.button_favorite);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MovieDetailActivity.this, "Add as favorite", Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(MOVIE, movie);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void loadTrailerData() {
        URL url = NetworkUtils.buildTrailerUrl(movie.getId());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(Constants.LOADER_TRAILER);

        if (loader == null)
            loaderManager.initLoader(Constants.LOADER_TRAILER, bundle, this);
        else
            loaderManager.restartLoader(Constants.LOADER_TRAILER, bundle, this);
    }

    private void loadReviewData() {
        URL url = NetworkUtils.buildReviewUrl(movie.getId());
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, url.toString());

        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> loader = loaderManager.getLoader(Constants.LOADER_REVIEW);

        if (loader == null)
            loaderManager.initLoader(Constants.LOADER_REVIEW, bundle, this);
        else
            loaderManager.restartLoader(Constants.LOADER_REVIEW, bundle, this);
    }

    private void displayData() {
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getBackdropPath())
                    .into(backdropImage);
            Picasso.with(this)
                    .load(movie.getPosterPath())
                    .into(posterImage);
            titleText.setText(movie.getTitle());
            String originalTitle = String.format(
                    "%s (%s)",
                    movie.getOriginalTitle(),
                    movie.getOriginalLanguage()
            );
            originalTitleText.setText(originalTitle);
            releaseText.setText(movie.getReleaseDate());
            movieRating.setRating(movie.getVoteAverage() / 2);
            String information = String.format(
                    "%.2f/10 of %d",
                    movie.getVoteAverage(),
                    movie.getVoteCount()
            );
            informationText.setText(information);
            overviewText.setText(movie.getOverview());
        }
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new MovieTaskLoader(this, args);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        switch (loader.getId()) {
            case Constants.LOADER_TRAILER: {
                try {
                    trailers = JsonUtils.getTrailers(data);
                    trailerAdapter.setTrailers(trailers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
            case Constants.LOADER_REVIEW: {
                try {
                    reviews = JsonUtils.getReviews(data);
                    reviewAdapter.setReviews(reviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onClick(Trailer trailer) {

    }
}
