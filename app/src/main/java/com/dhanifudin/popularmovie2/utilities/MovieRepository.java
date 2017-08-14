package com.dhanifudin.popularmovie2.utilities;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.dhanifudin.popularmovie2.data.MovieContract.MovieEntry;
import com.dhanifudin.popularmovie2.model.Movie;

/**
 * Created by dhanifudin on 8/5/17.
 */

public class MovieRepository {
    private ContentResolver resolver;

    public MovieRepository(ContentResolver resolver) {
        this.resolver = resolver;
    }

    public boolean addFavoriteMovie(Movie movie) {
        final ContentValues values = getMovieValue(movie);
        Uri uri = resolver.insert(MovieEntry.CONTENT_URI, values);
        return uri != null;
    }

    public boolean removeFavoriteMovie(Movie movie) {
        Uri uri = MovieEntry.CONTENT_URI;
        uri = uri.buildUpon().appendPath(movie.getId()).build();
        int result = resolver.delete(uri, null, null);
        return result > 0;
    }

    public boolean isFavoriteMovie(Movie movie) {
        final String where = String.format("%s=?", MovieEntry.COLUMN_MOVIE_ID);
        final String[] args = new String[] { String.valueOf(movie.getId()) };
        Cursor cursor = resolver.query(MovieEntry.CONTENT_URI, MovieEntry.MOVIE_PROJECTION, where, args, null);
        return (cursor != null && cursor.getCount() >= 1);
    }

    private ContentValues getMovieValue(Movie movie) {
        final ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MovieEntry.COLUMN_MOVIE_TITLE, movie.getTitle());
        values.put(MovieEntry.COLUMN_MOVIE_POSTER, movie.getPosterPath());
        return values;
    }
}
