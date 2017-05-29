package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiReviewResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiVideoResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ReviewObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject;
import com.annamorgiel.popularmovies_udacity_1.ReviewAdapter;
import com.annamorgiel.popularmovies_udacity_1.VideoAdapter;
import com.annamorgiel.popularmovies_udacity_1.app.App;
import com.annamorgiel.popularmovies_udacity_1.data.MovieContract;
import com.annamorgiel.popularmovies_udacity_1.data.MovieDbHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    private Integer movieId = null;
    private SQLiteDatabase db;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private MovieObject movie;
    private VideoAdapter videoAdapter;
    private View.OnClickListener videoListener;
    private List<VideoObject> videoList;

    private View.OnClickListener favButtonListener;
    private ReviewAdapter reviewAdapter;
    private View.OnClickListener reviewListener;
    private List<ReviewObject> reviewList;

    @BindView(R.id.detail_poster_iv)
    ImageView poster_detail;
    @BindView(R.id.detail_movie_title)
    TextView title;
    @BindView(R.id.detail_release_date_tv)
    TextView release_date;
    @BindView(R.id.detail_movie_length_tv)
    TextView length;
    @BindView(R.id.rdetail_anking_tv)
    TextView ranking;
    @BindView(R.id.detail_movie_description)
    TextView desc;
    @BindView(R.id.rv_videos)
    RecyclerView trailers_rv;
    @BindView(R.id.rv_reviews)
    RecyclerView reviews_rv;
    @BindView(R.id.detail_favorites_button)
    Button fav;
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


        trailers_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        videoAdapter = new VideoAdapter(videoListener);
        trailers_rv.setAdapter(videoAdapter);
        trailers_rv.setHasFixedSize(true);
        fetchVideos(movieId);

        reviews_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewAdapter = new ReviewAdapter(reviewListener);
        reviews_rv.setAdapter(reviewAdapter);
        reviews_rv.setHasFixedSize(true);
        fetchReviews(movieId);
        fav.setTag(1);
        fav.setText("mark movie as favorite");
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 1) {
                    addMovieToFavourites(movie);
                    fav.setText("remove movie from favorites");
                    v.setTag(0);
                } else {
                    removeMovieFromFavourites(movie.getId());
                    fav.setText("mark movie as favorite");
                    v.setTag(1);
                }
            }
        });
    }

    public void addMovieToFavourites(MovieObject movie) {

        ContentValues cv = new ContentValues();
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH, movie.getPosterPath());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ADULT, movie.getAdult());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE, movie.getReleaseDate());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_RUNTIME, movie.getRuntime());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE, movie.getTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH, movie.getBackdropPath());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_POPULARITY, movie.getPopularity());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VIDEO, movie.getVideo());
        cv.put(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());

        Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, cv);

        if (uri != null) {
            //Toast.makeText(getBaseContext(), "Yay! New favourite Movie!",
            Toast.makeText(getBaseContext(), uri.toString(),
                    Toast.LENGTH_LONG).show();
        }
        //db.insert(MovieContract.MovieEntry.TABLE_NAME, null, cv);
    }


    private void fetchMovieDetails(Integer id) {
        final Call movieDetailCall = App.getRestClient().getMovieService().getMovieDetails(id, THE_MOVIE_DB_API_KEY);
        movieDetailCall.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                // get raw response

                movie = response.body();
                title.setText(movie.getTitle());
                release_date.setText(movie.getReleaseDate());
                desc.setText(movie.getOverview());
                length.setText(movie.getRuntime().toString() + " min");
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

    private void fetchVideos(Integer id) {
        final Call videoCall = App.getRestClient().getMovieService().getVideos(id, THE_MOVIE_DB_API_KEY);
        videoCall.enqueue(new Callback<List<VideoObject>>() {
            @Override
            public void onResponse(Call<List<VideoObject>> call, Response<List<VideoObject>> response) {
                // get raw response
                ApiVideoResponse videoResponse = (ApiVideoResponse) response.body();
                videoList = videoResponse.getVideoObjects();
                videoAdapter.setVideoList(videoList);
            }

            @Override
            public void onFailure(Call<List<VideoObject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void fetchReviews(Integer id) {
        final Call videoCall = App.getRestClient().getMovieService().getReviews(id, THE_MOVIE_DB_API_KEY);
        videoCall.enqueue(new Callback<List<ReviewObject>>() {
            @Override
            public void onResponse(Call<List<ReviewObject>> call, Response<List<ReviewObject>> response) {
                // get raw response
                ApiReviewResponse reviewResponse = (ApiReviewResponse) response.body();
                reviewList = reviewResponse.getReviewObjects();
                reviewAdapter.setReviewList(reviewList);
            }

            @Override
            public void onFailure(Call<List<ReviewObject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean removeMovieFromFavourites(Integer id) {
        return db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID + "=" + id, null) > 0;
    }

    /*private long addNewFavouriteMovie(String posterPath, Boolean adult, String overview, String releaseDate, Integer runtime, String originalTitle,
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
    }*/
}

