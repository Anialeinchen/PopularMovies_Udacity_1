package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.app.App;
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
    String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    MovieObject movie;
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


    }

