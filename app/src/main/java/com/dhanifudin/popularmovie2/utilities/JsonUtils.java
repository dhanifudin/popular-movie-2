package com.dhanifudin.popularmovie2.utilities;

import com.dhanifudin.popularmovie2.model.Movie;
import com.dhanifudin.popularmovie2.model.Review;
import com.dhanifudin.popularmovie2.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class JsonUtils {

    static final String RESULTS = "results";

    static final String MOVIE_VOTE_COUNT = "vote_count";
    static final String MOVIE_VOTE_AVERAGE = "vote_average";
    static final String MOVIE_ID = "id";
    static final String MOVIE_TITLE = "title";
    static final String MOVIE_POSTER_PATH = "poster_path";
    static final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
    static final String MOVIE_ORIGINAL_TITLE = "original_title";
    static final String MOVIE_BACKDROP_PATH = "backdrop_path";
    static final String MOVIE_OVERVIEW = "overview";
    static final String MOVIE_RELEASE_DATE = "release_date";

    static final String TRAILER_ID = "id";
    static final String TRAILER_KEY = "key";
    static final String TRAILER_NAME = "name";
    static final String TRAILER_SITE = "site";
    static final String TRAILER_TYPE = "type";

    static final String REVIEW_ID = "id";
    static final String REVIEW_AUTHOR = "author";
    static final String REVIEW_CONTENT = "content";
    static final String REVIEW_URL = "url";

    public static ArrayList<Movie> getMovies(String json) throws JSONException {
        ArrayList<Movie> movies = null;
        JSONObject moviesJson = new JSONObject(json);
        if (moviesJson.has(RESULTS)) {
            JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
            movies = new ArrayList<>();

            for (int i = 0; i < moviesArray.length(); i++) {
                JSONObject movieJson = moviesArray.getJSONObject(i);
                Movie movie = new Movie();
                String id = movieJson.getString(MOVIE_ID);
                movie.setId(id);

                String title = movieJson.getString(MOVIE_TITLE);
                movie.setTitle(title);

                String originalTitle = movieJson.getString(MOVIE_ORIGINAL_TITLE);
                movie.setOriginalTitle(originalTitle);

                String overview = movieJson.getString(MOVIE_OVERVIEW);
                movie.setOverview(overview);

                String originalLanguage = movieJson.getString(MOVIE_ORIGINAL_LANGUAGE);
                movie.setOriginalLanguage(originalLanguage);

                String releaseDate = movieJson.getString(MOVIE_RELEASE_DATE);
                movie.setReleaseDate(releaseDate);

                int voteCount = movieJson.getInt(MOVIE_VOTE_COUNT);
                movie.setVoteCount(voteCount);

                double voteAverage = movieJson.getDouble(MOVIE_VOTE_AVERAGE);
                movie.setVoteAverage((float) voteAverage);

                String posterPath = movieJson.getString(MOVIE_POSTER_PATH);
                movie.setPosterPath(posterPath);

                String backdropPath = movieJson.getString(MOVIE_BACKDROP_PATH);
                movie.setBackdropPath(backdropPath);

                movies.add(movie);
            }
        }
        return movies;
    }

    public static List<Trailer> getTrailers(String json) throws JSONException {
        List<Trailer> trailers = null;
        JSONObject trailersJson = new JSONObject(json);
        if (trailersJson.has(RESULTS)) {
            JSONArray trailersArray = trailersJson.getJSONArray(RESULTS);
            trailers = new ArrayList<>();
            for (int i = 0; i < trailersArray.length(); i++) {
                JSONObject trailerJson = trailersArray.getJSONObject(i);
                Trailer trailer = new Trailer();
                String id = trailerJson.getString(TRAILER_ID);
                trailer.setId(id);

                String key = trailerJson.getString(TRAILER_KEY);
                trailer.setKey(key);

                String name = trailerJson.getString(TRAILER_NAME);
                trailer.setName(name);

                String site = trailerJson.getString(TRAILER_SITE);
                trailer.setSite(site);

                String type = trailerJson.getString(TRAILER_TYPE);
                trailer.setType(type);

                trailers.add(trailer);
            }
        }
        return trailers;
    }

    public static List<Review> getReviews(String json) throws JSONException {
        List<Review> reviews = null;
        JSONObject reviewsJson = new JSONObject(json);
        if (reviewsJson.has(RESULTS)) {
            JSONArray reviewsArray = reviewsJson.getJSONArray(RESULTS);
            reviews = new ArrayList<>();
            for (int i = 0; i < reviewsArray.length(); i++) {
                JSONObject reviewJson = reviewsArray.getJSONObject(i);
                Review review = new Review();
                String id = reviewJson.getString(REVIEW_ID);
                review.setId(id);

                String author = reviewJson.getString(REVIEW_AUTHOR);
                review.setAuthor(author);

                String content = reviewJson.getString(REVIEW_CONTENT);
                review.setContent(content);

                String url = reviewJson.getString(REVIEW_URL);
                review.setUrl(url);

                reviews.add(review);
            }
        }
        return reviews;
    }
}
