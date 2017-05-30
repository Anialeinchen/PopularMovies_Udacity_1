package com.annamorgiel.popularmovies_udacity_1.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.annamorgiel.popularmovies_udacity_1.Rest.model.FavoritesItem;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;


/**
 * Created by Anna Morgiel on 23.04.2017.
 * Displays movie grid. Sort by popular, top rated or favourite movies.
 * Popular and top rated will be realised via retrofit and favorites retrieved from a db.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String ON_SAVED_INSTANCE_KEY= "movies";
    public static RestClient mRestClient = new RestClient();
    public static final String MOVIES = "movies";

    private MovieAdapter movieAdapter;
    private List<MovieObject> movieList;
    private View.OnClickListener movieListener;

    private String sortByPopular = "popular";
    private String sortByHighestRated = "top_rated";
    //realmComponents 1
    private Realm realmInstance;
    private Context mContext;

    @BindView(R.id.rv_movies)
    public RecyclerView poster_rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(MOVIES);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //realmComponents 2
        initRealm();
        mContext = this;
        mRestClient.getMovieService();
        //adjust the layout depending on the orientation
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
        //perform a network request
        fetchMovies(sortByPopular);

        movieListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        };

        movieAdapter = new MovieAdapter(movieListener);
        poster_rv.setAdapter(movieAdapter);
        poster_rv.setHasFixedSize(true);
    }

    //realmComponents 3
    private void initRealm() {
        Realm.init(this);
        realmInstance = Realm.getDefaultInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.sort_by_popularity:
                fetchMovies(sortByPopular);
                movieAdapter.setMovieList(movieList);
                movieAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_ranking:
                fetchMovies(sortByHighestRated);
                movieAdapter.setMovieList(movieList);
                movieAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_by_favourites:
                List<MovieObject> list = getFavoriteMoviesList();
                movieAdapter.setMovieList(list);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private List<MovieObject> getFavoriteMoviesList() {
        List<MovieObject> list = new ArrayList<>();
//realmComponents 4
        final RealmResults<FavoritesItem> allFavoriteIds = realmInstance.where(FavoritesItem.class).findAll();
        final List<MovieObject> movieList = movieAdapter.getMovieList();

        for (FavoritesItem favItem : allFavoriteIds) {
            for (MovieObject movie : movieList) {
                if (favItem.getMovieId().equals(movie.getId())) {
                    list.add(movie);
                }
            }
        }
        return list;
    }


    private void fetchMovies(String sortby) {
        final Call movieListCall = mRestClient.getMovieService().getMovies(sortby, THE_MOVIE_DB_API_KEY);
        movieListCall.enqueue(new Callback<List<MovieObject>>() {
            @Override
            public void onResponse(Call<List<MovieObject>> call, Response<List<MovieObject>> response) {
                ApiResponse movieResponse = (ApiResponse) response.body();
                movieList = movieResponse.getMovieObjects();
                movieAdapter.setMovieList(movieList);
                movieAdapter.notifyDataSetChanged();
                Log.d(TAG, "onResponse: size:" + movieList.size());

            }

            @Override
            public void onFailure(Call<List<MovieObject>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.check_connection,
                        Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIES, (ArrayList<? extends Parcelable>) movieList);
    }

}