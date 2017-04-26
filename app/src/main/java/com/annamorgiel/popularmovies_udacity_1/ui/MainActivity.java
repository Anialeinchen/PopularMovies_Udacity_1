package com.annamorgiel.popularmovies_udacity_1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.annamorgiel.popularmovies_udacity_1.MovieAdapter;
import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickListener{

    private RecyclerView poster_rv;
    private MovieAdapter adapter;
    private String defaultSortBy = "popular";
    private static final int NUM_GRID_ITEM = 100;
    private List<MovieObject> movieList;
    private MovieAdapter.GridItemClickListener listener;
    private static RestClient mRestClient = new RestClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRestClient.getMovieService();

        //find RecyclerView and set GridLayoutManager to handle ViewHolders in a grid
        poster_rv = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this,NUM_GRID_ITEM);
        poster_rv.setLayoutManager(layoutManager);
        fetchMovies(defaultSortBy);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String sortByTopRated = "top_rated";
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_by_popularity:
                fetchMovies(defaultSortBy);
                return true;

            case R.id.sort_by_ranking:
                fetchMovies(sortByTopRated);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGridItemClick(int clickedItemIndex) {
        Class destinationClass = DetailActivity.class;
        Long clickedItemId = adapter.getItemId(clickedItemIndex);
        Intent intentToStartDetailActivity = new Intent(getApplicationContext(), destinationClass);
        intentToStartDetailActivity.putExtra("movieId",clickedItemId);
        startActivity(intentToStartDetailActivity);
    }

    private void fetchMovies(String sortby){
        final Call movieListCall = mRestClient.getMovieService().getMovies(sortby, THE_MOVIE_DB_API_KEY);
        movieListCall.enqueue(new Callback<List<MovieObject>>() {
            @Override
            public void onResponse(Call<List<MovieObject>> call, Response<List<MovieObject>> response) {
                // get raw response
                ApiResponse movieResponse = (ApiResponse) response.body();
                movieList = movieResponse.getMovieObjects();
                //movieList = response.body();
                adapter = new MovieAdapter(movieList, listener);
                poster_rv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<MovieObject>> call, Throwable t) {}
        });
    }
}
