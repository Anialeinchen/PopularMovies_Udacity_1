package com.annamorgiel.popularmovies_udacity_1.ui;

import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.annamorgiel.popularmovies_udacity_1.MovieAdapter;
import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.data.MovieContract;
import com.annamorgiel.popularmovies_udacity_1.data.MovieDbHelper;

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

public class MainActivity extends AppCompatActivity{
    private static final String TAG = "MainActivity";

    private SQLiteDatabase db;

    private MovieAdapter movieAdapter;
    private String sortByPopular = "popular";
    private String sortByHighestRated = "top_rated";
    private List<MovieObject> movieList;
    private View.OnClickListener movieListener;
    private static RestClient mRestClient = new RestClient();
    @BindView(R.id.rv_movies) RecyclerView poster_rv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRestClient.getMovieService();

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        else{
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
        //todo Ania: change adapter to accept cursor and context

        movieAdapter = new MovieAdapter(movieListener);
        poster_rv.setAdapter(movieAdapter);
        poster_rv.setHasFixedSize(true);
        fetchMovies(sortByPopular);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_by_popularity:
                fetchMovies(sortByPopular);
                movieAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_ranking:
                fetchMovies(sortByHighestRated);
                movieAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_by_favourites:
                MovieDbHelper dbHelper = new MovieDbHelper(this);
                db = dbHelper.getWritableDatabase();
                Cursor cursor = getAllMovies();
                movieAdapter = new MovieAdapter(getBaseContext(), cursor.getCount(), movieListener);
                movieAdapter.notifyDataSetChanged();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllMovies(){
        return db.query(MovieContract.MovieEntry.TABLE_NAME, null,null,null,null,null, MovieContract.MovieEntry.COLUMN_NAME_TITLE);
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
                Log.d(TAG, "onResponse: size:" + movieList.size());
                movieAdapter.setMovieList(movieList);
            }

            @Override
            public void onFailure(Call<List<MovieObject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}














































