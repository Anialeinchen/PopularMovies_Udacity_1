package com.annamorgiel.popularmovies_udacity_1.ui;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annamorgiel.popularmovies_udacity_1.MovieAdapter;
import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.service.MovieService;
import com.annamorgiel.popularmovies_udacity_1.app.App;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;

public class MainActivity extends AppCompatActivity {

    private RecyclerView poster_rv;
    private MovieAdapter adapter;
    private String defaultSortBy = "popular";
    private static final int NUM_GRID_ITEM = 100;
    private List<MovieObject> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find RecyclerView and set GridLayoutManager to handle ViewHolders in a grid
        poster_rv = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this,NUM_GRID_ITEM);
        poster_rv.setLayoutManager(layoutManager);

        Call movieListCall = App.getRestClient().getMovieService().getMovies(defaultSortBy, THE_MOVIE_DB_API_KEY);
        movieListCall.enqueue(new Callback<List<MovieObject>>() {
            @Override
            public void onResponse(Call<List<MovieObject>> call, Response response) {
                if(!(response == null)){
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

            }
        });
        adapter = new MovieAdapter();
    }
}
