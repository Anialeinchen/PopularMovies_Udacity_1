package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.app.App;
import com.example.Example;
import com.squareup.picasso.Picasso;

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
    Example movie;
    ImageView poster_detail;
    TextView title;
    TextView release_date;
    //TextView length = (TextView) findViewById(R.id.detail_movie_length_tv);
    TextView ranking;
    TextView desc;
    //TextView trailer = (TextView) findViewById(R.id.trailer1_tv);
    private static RestClient mRestClient;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mRestClient = new RestClient();
        poster_detail = (ImageView) findViewById(R.id.detail_poster_iv);
        title = (TextView) this.findViewById(R.id.detail_movie_title);
        release_date = (TextView) findViewById(R.id.detail_release_date_tv);
        ranking = (TextView) findViewById(R.id.rdetail_anking_tv);
        desc = (TextView) findViewById(R.id.detail_movie_description);
        mRestClient.getMovieService();
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movieId = intentThatStartedThisActivity.getIntExtra("movieId", 22);
        }
        fetchMovieDetails(movieId);
    }

    private void fetchMovieDetails(Integer id){
        final Call movieDetailCall = App.getRestClient().getMovieService().getMovieDetails(id, THE_MOVIE_DB_API_KEY);
        movieDetailCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                // get raw response
                movie = response.body();
                title.setText(movie.getTitle());
                release_date.setText(movie.getReleaseDate());
                desc.setText(movie.getOverview());
                ranking.setText(movie.getPopularity().toString());
                Picasso.with(getApplicationContext())
                        .load(BASE_POSTER_URL + movie.getPosterPath())
                        .into(poster_detail);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {}
        });
    }


    }

