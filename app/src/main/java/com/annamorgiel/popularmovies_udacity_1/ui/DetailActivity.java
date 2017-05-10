package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject;
import com.annamorgiel.popularmovies_udacity_1.app.App;
import com.annamorgiel.popularmovies_udacity_1.data.MovieContract;
import com.annamorgiel.popularmovies_udacity_1.data.MovieDbHelper;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class DetailActivity extends Activity {

    Long clickedItemId = null;
    Integer movieId = null;
    private Button fav;
    String videoKey = null;
    private SQLiteDatabase db;
    String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    MovieObject movie;
    @BindView(R.id.listview) ListView listView;
    @BindView(R.id.detail_poster_iv) ImageView poster_detail;
    @BindView(R.id.detail_movie_title) TextView title;
    @BindView(R.id.detail_release_date_tv) TextView release_date;
    @BindView(R.id.detail_movie_length_tv) TextView length;
    @BindView(R.id.rdetail_anking_tv) TextView ranking;
    @BindView(R.id.detail_movie_description) TextView desc;
    //TextView trailer = (TextView) findViewById(R.id.trailer1_tv);
    private static RestClient mRestClient = new RestClient();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        mRestClient.getMovieService();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movieId")) {
            movieId = intentThatStartedThisActivity.getIntExtra("movieId", 22);
        }
        fetchMovieDetails(movieId);

        MovieDbHelper dbHelper = new MovieDbHelper(this);
        db = dbHelper.getWritableDatabase();

        Cursor cursor = getAllMovies();
    }

    private void fetchMovieDetails(Integer id){
        final Call movieDetailCall = App.getRestClient().getMovieService().getMovieDetails(id, THE_MOVIE_DB_API_KEY);
         movieDetailCall.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                // get raw response

                movie = response.body();
                title.setText(movie.getTitle());
                release_date.setText(movie.getReleaseDate());
                desc.setText(movie.getOverview());
                length.setText(movie.getRuntime().toString()+" min");
                ranking.setText(movie.getVoteAverage().toString());
                Picasso.with(getApplicationContext())
                        .load(BASE_POSTER_URL + movie.getPosterPath())
                        .placeholder(R.drawable.loading_poster)
                        .error(R.drawable.ic_alert_circle)
                        .into(poster_detail);
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void watchYoutubeVideo(String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
    private void fetchVideos(Integer id){
        final Call movieDetailCall = App.getRestClient().getMovieService().getVideos(id, THE_MOVIE_DB_API_KEY);
        movieDetailCall.enqueue(new Callback<VideoObject>() {
            @Override
            public void onResponse(Call<VideoObject> call, Response<VideoObject> response) {
                // get raw response

               videoKey = response.body().getKey();
            }

            @Override
            public void onFailure(Call<VideoObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    private Cursor getAllMovies(){
        return db.query(MovieContract.MovieEntry.TABLE_NAME, null,null,null,null,null, MovieContract.MovieEntry.COLUMN_NAME_TITLE);
    }

    private long addNewFavouriteMovie(String posterPath, Boolean adult, String overview, String releaseDate, Integer runtime, String originalTitle,
                                      String originalLanguage, String title, String backdropPath, Double popularity, Integer voteCount,
                                      Boolean video, Double voteAverage){
        fav = (Button) findViewById(R.id.detail_favorites_button);

        Toast.makeText(getApplicationContext(), "Yay! New favourite Movie!",
                Toast.LENGTH_LONG).show();

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH, posterPath);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ADULT, adult);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW, overview);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE, releaseDate);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_RUNTIME, runtime);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE, originalTitle);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE, originalLanguage);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_TITLE,title);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH, backdropPath);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_POPULARITY, popularity);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT, voteCount);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VIDEO, video);
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, voteAverage);

        return db.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
    }
    }

