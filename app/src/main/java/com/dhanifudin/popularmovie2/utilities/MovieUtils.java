package com.dhanifudin.popularmovie2.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;

import com.dhanifudin.popularmovie2.data.MovieContract.MovieEntry;
import com.dhanifudin.popularmovie2.model.Movie;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieUtils {

    private ContentResolver contentResolver;

    public MovieUtils(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public boolean isFavorite(String id) {
        return false;
    }

//    public Movie[] getFavoriteMovies() {
//        return null;
//    }
//
//    public Movie[] getPopularMovies() {
//        return null;
//    }
//
//    public Movie[] getTopRatedMovies() {
//        return null;
//    }

    public boolean addFavoriteMovie(Movie movie) {
        final ContentValues values = getMovieValue(movie);
        Uri uri = contentResolver.insert(MovieEntry.CONTENT_URI, values);
        return uri != null;
    }

    public boolean removeFavoriteMovie(Movie movie) {
//        final ContentValues values = getMovieValue(movie);
//        Uri uri = contentResolver.delete(MovieEntry.CONTENT_URI, values);
//        return uri != null;
        return false;
    }

    private ContentValues getMovieValue(Movie movie) {
        final ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(MovieEntry.COLUMN_MOVIE_POSTER, movie.getPosterPath());
        return values;
    }
}
