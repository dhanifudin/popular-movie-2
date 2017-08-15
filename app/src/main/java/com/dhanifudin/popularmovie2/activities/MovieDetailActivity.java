package com.dhanifudin.popularmovie2.activities;

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

import com.dhanifudin.popularmovie2.utilities.MovieRepository;
import com.dhanifudin.popularmovie2.R;
import com.dhanifudin.popularmovie2.adapters.ReviewAdapter;
import com.dhanifudin.popularmovie2.adapters.TrailerAdapter;
import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.model.Trailer;
import com.dhanifudin.popularmovie2.tasks.ReviewTaskLoader;
import com.dhanifudin.popularmovie2.tasks.TrailerTaskLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.dhanifudin.popularmovie2.Constants.MOVIE;

public class MovieDetailActivity extends AppCompatActivity implements TrailerAdapter.TrailerAdapterOnClickHandler {

    private Movie movie;

    @BindView(R.id.image_backdrop)
    ImageView backdropImage;
    @BindView(R.id.image_poster)
    ImageView posterImage;
    @BindView(R.id.text_title)
    TextView titleText;
    @BindView(R.id.text_original_title)
    TextView originalTitleText;
    @BindView(R.id.text_release)
    TextView releaseText;
    @BindView(R.id.rating_movie)
    RatingBar movieRating;
    @BindView(R.id.text_information)
    TextView informationText;
    @BindView(R.id.text_overview)
    TextView overviewText;
    @BindView(R.id.button_favorite)
    Button favoriteButton;
    @BindView(R.id.trailer_list)
    RecyclerView trailerView;
    @BindView(R.id.review_list)
    RecyclerView reviewView;

//    private TrailerAdapter trailerAdapter;
//    private ReviewAdapter reviewAdapter;

//    private TrailerTaskLoader trailerTaskLoader;
//    private ReviewTaskLoader reviewTaskLoader;

    private MovieRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE)) {
            Bundle bundle = getIntent().getExtras();
            movie = bundle.getParcelable(MOVIE);
        } else {
            movie = savedInstanceState.getParcelable(MOVIE);
        }

        RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        trailerView.setLayoutManager(trailerLayoutManager);
        trailerView.setHasFixedSize(true);
        TrailerAdapter trailerAdapter = new TrailerAdapter(this);
        trailerView.setAdapter(trailerAdapter);

        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewView.setLayoutManager(reviewLayoutManager);
        reviewView.setHasFixedSize(true);
        ReviewAdapter reviewAdapter = new ReviewAdapter();
        reviewView.setAdapter(reviewAdapter);

        TrailerTaskLoader trailerTaskLoader = new TrailerTaskLoader(this, trailerAdapter);
        ReviewTaskLoader reviewTaskLoader = new ReviewTaskLoader(this, reviewAdapter);
        repository = new MovieRepository(getContentResolver());

        trailerTaskLoader.loadData(getSupportLoaderManager(), movie);
        reviewTaskLoader.loadData(getSupportLoaderManager(), movie);
        displayData();

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
