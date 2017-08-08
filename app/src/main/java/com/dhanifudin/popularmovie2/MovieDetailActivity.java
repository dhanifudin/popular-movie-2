package com.dhanifudin.popularmovie2;

import android.os.Bundle;
import android.os.PersistableBundle;
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
import com.dhanifudin.popularmovie2.tasks.ReviewTaskLoader;
import com.dhanifudin.popularmovie2.tasks.TrailerTaskLoader;
import com.dhanifudin.popularmovie2.utilities.MovieUtils;
import com.squareup.picasso.Picasso;

import static com.dhanifudin.popularmovie2.Constants.MOVIE;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    private Movie movie;

    private ImageView backdropImage;
    private ImageView posterImage;
    private TextView titleText;
    private TextView originalTitleText;
    private TextView releaseText;
    private RatingBar movieRating;
    private TextView informationText;
    private TextView overviewText;
    private Button favoriteButton;

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;

    private TrailerTaskLoader trailerTaskLoader;
    private ReviewTaskLoader reviewTaskLoader;

    private MovieRepository repository;

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
        trailerView.setAdapter(trailerAdapter);

        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewView.setLayoutManager(reviewLayoutManager);
        reviewView.setHasFixedSize(true);
        reviewAdapter = new ReviewAdapter();
        reviewView.setAdapter(reviewAdapter);

        trailerTaskLoader = new TrailerTaskLoader(this, trailerAdapter);
        reviewTaskLoader = new ReviewTaskLoader(this, reviewAdapter);
        repository = new MovieRepository(getContentResolver());

        trailerTaskLoader.loadData(getSupportLoaderManager(), movie);
        reviewTaskLoader.loadData(getSupportLoaderManager(), movie);
        displayData();

        favoriteButton = (Button) findViewById(R.id.button_favorite);
        if (repository.isFavoriteMovie(movie)) {
            toggleRemoveFavoriteButton();
        } else {
            toggleAddFavoriteButton();
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("ADD".equals(favoriteButton.getTag())) {
                    boolean success = repository.addFavoriteMovie(movie);
                    if (success) {
                        toggleRemoveFavoriteButton();
                    }
                } else {
                    boolean success = repository.removeFavoriteMovie(movie);
                    if (success) {
                        toggleAddFavoriteButton();
                    }
                }
            }
        });
    }

    private void toggleRemoveFavoriteButton() {
        favoriteButton.setText(getResources().getString(R.string.remove_as_favorite));
        favoriteButton.setTag("REMOVE");
    }

    private void toggleAddFavoriteButton() {
        favoriteButton.setText(getResources().getString(R.string.add_as_favorite));
        favoriteButton.setTag("ADD");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putParcelable(MOVIE, movie);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void displayData() {
        if (movie != null) {
            Picasso.with(this)
                    .load(movie.getBackdropUrl())
                    .into(backdropImage);
            Picasso.with(this)
                    .load(movie.getPosterUrl())
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
    public void onClick(Trailer trailer) {

    }
}
