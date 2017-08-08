package com.dhanifudin.popularmovie2.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieContract {

    public static final String CONTENT_AUTHORITY = "com.dhanifudin.popularmovie2";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns {

        public static final String[] MOVIE_PROJECTION = new String[] {
            MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_MOVIE_TITLE,
            MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE,
            MovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieEntry.COLUMN_MOVIE_ORIGINAL_LANGUAGE,
            MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
            MovieEntry.COLUMN_MOVIE_VOTE_COUNT,
            MovieEntry.COLUMN_MOVIE_VOTE_AVERAGE,
            MovieEntry.COLUMN_MOVIE_POSTER,
            MovieEntry.COLUMN_MOVIE_BACKDROP,
        };

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_MOVIE_RELEASE_DATE = "Release_date";
        public static final String COLUMN_MOVIE_VOTE_COUNT = "VOTe_count";
        public static final String COLUMN_MOVIE_VOTE_AVERAGE = "Vote_average";
        public static final String COLUMN_MOVIE_POSTER = "movie_poster";
        public static final String COLUMN_MOVIE_BACKDROP = "movie_backdrop";

    }

}
