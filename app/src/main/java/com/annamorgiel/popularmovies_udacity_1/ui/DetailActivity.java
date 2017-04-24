package com.annamorgiel.popularmovies_udacity_1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.app.App;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class DetailActivity extends AppCompatActivity {

    Long clickedItemId = null;
    Long movieId = null;
    String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    MovieObject movie;
    ImageView poster = (ImageView) findViewById(R.id.detail_poster_iv);
    TextView title = (TextView) findViewById(R.id.detail_movie_title);
    TextView release_date = (TextView) findViewById(R.id.detail_release_date_tv);
    TextView length = (TextView) findViewById(R.id.detail_movie_length_tv);
    TextView ranking = (TextView) findViewById(R.id.rdetail_anking_tv);
    TextView desc = (TextView) findViewById(R.id.detail_movie_description);
    TextView trailer = (TextView) findViewById(R.id.trailer1_tv);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
            movieId = intentThatStartedThisActivity.getLongExtra("movieId", 1L);
        }
        fetchMovieDetails(movieId);
    }

    private void fetchMovieDetails(Long id){
        final Call movieDetailCall = App.getRestClient().getMovieService().getMovieDetails(id, THE_MOVIE_DB_API_KEY);
        movieDetailCall.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                // get raw response
                movie = response.body();
                title.setText(movie.getTitle());
                release_date.setText(movie.getReleaseDate());
                desc.setText(movie.getOverview());
                ranking.setText(movie.getPopularity().toString());
                Picasso.with(getApplicationContext()).load(BASE_POSTER_URL + movie.getPosterPath()).into(poster);
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {}
        });
    }


    }

