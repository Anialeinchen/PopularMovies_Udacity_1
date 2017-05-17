package com.annamorgiel.popularmovies_udacity_1.data;

import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by Anna Morgiel on 04.05.2017.
 */

public class MovieContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private MovieContract() {
    }

    public static final String AUTHORITY = "com.annamorgiel.popularmovies_udacity_1";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_FAVORITE_MOVIES  = "movies";
    //todo implement BASE_CONTENT_URI
    //Define the possible paths for accessing data in this contract
    /* Inner class that defines the table contents */
    public static class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();
        //add rest
        //perhaps unnecessary
        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_NAME_POSTER_PATH = "posterPath";

        public static final String COLUMN_NAME_ADULT = "adult";

        public static final String COLUMN_NAME_OVERVIEW = "overview";

        public static final String COLUMN_NAME_RELEASE_DATE = "releaseDate";

        //perhaps unnecessary
        public static final String COLUMN_NAME_ID = "id";

        public static final String COLUMN_NAME_RUNTIME = "runtime";

        public static final String COLUMN_NAME_ORIGINAL_TITLE = "originalTitle";

        public static final String COLUMN_NAME_ORIGINAL_LANGUAGE = "originalLanguage";

        public static final String COLUMN_NAME_TITLE = "title";

        public static final String COLUMN_NAME_BACKDROP_PATH = "backdropPath";

        public static final String COLUMN_NAME_POPULARITY = "popularity";

        public static final String COLUMN_NAME_VOTE_COUNT = "voteCount";

        public static final String COLUMN_NAME_VIDEO = "video";

        public static final String COLUMN_NAME_VOTE_AVERAGE = "voteAverage";
    }
}

