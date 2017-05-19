package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
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
import com.annamorgiel.popularmovies_udacity_1.data.MovieContentProvider;
import com.annamorgiel.popularmovies_udacity_1.data.MovieContract;
import com.annamorgiel.popularmovies_udacity_1.data.MovieDbHelper;

import java.util.ArrayList;
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

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "MainActivity";
    private static RestClient mRestClient = new RestClient();
    private final Context mContext;
    private static final int FAVORITE_MOVIES_LOADER = 100;
    private Cursor mCursor;
    @BindView(R.id.rv_movies)
    private RecyclerView poster_rv;
    private SQLiteDatabase db;
    private MovieAdapter movieAdapter;
    private String sortByPopular = "popular";
    private String sortByHighestRated = "top_rated";
    private List<MovieObject> movieList;
    private List<MovieObject> favouriteMoviesFromDB;
    //todo: movieListener is never assigned?
    private View.OnClickListener movieListener;

    public static List<MovieObject> parseMoviesFromCursor(Cursor cursor) {
        List<MovieObject> movieObjects = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String posterPath = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH));
                Integer adult = cursor.getInt(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_ADULT));
                String overview = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW));
                String releaseDate = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE));
                //todo gendre IDs?
                Integer id = cursor.getInt(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_ID));
                Integer runtime = cursor.getInt(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_RUNTIME));
                String originalTitle = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE));
                String originalLanguage = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE));
                String title = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_TITLE));
                String backdropPath = cursor.getString(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH));
                Double popularity = cursor.getDouble(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_POPULARITY));
                Integer voteCount = cursor.getInt(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT));
                Integer video = cursor.getInt(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_VIDEO));
                Double voteAverage = cursor.getDouble(
                        cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
                Boolean adult_boolean = (adult != 0);

                Boolean video_boolean = (video != 0);

                movieObjects.add(new MovieObject(posterPath, adult_boolean, overview, releaseDate, id, runtime, originalTitle, originalLanguage,
                        title, backdropPath, popularity, voteCount, video_boolean, voteAverage));
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "Table is empty");
        }
        return movieObjects;
    }

    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        //todo notifyDataSetChanged error: cannot resolve method
        notifyDataSetChanged();
    }

    public static final String[] DMOVIE_DETAILS_PROJECTION = {
            MovieContract.MovieEntry.COLUMN_NAME_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_NAME_ADULT,
            MovieContract.MovieEntry.COLUMN_NAME_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_NAME_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_NAME_RUNTIME,
            MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_TITLE,
            MovieContract.MovieEntry.COLUMN_NAME_ORIGINAL_LANGUAGE,
            MovieContract.MovieEntry.COLUMN_NAME_TITLE,
            MovieContract.MovieEntry.COLUMN_NAME_BACKDROP_PATH,
            MovieContract.MovieEntry.COLUMN_NAME_POPULARITY,
            MovieContract.MovieEntry.COLUMN_NAME_VOTE_COUNT,
            MovieContract.MovieEntry.COLUMN_NAME_VIDEO,
            MovieContract.MovieEntry.COLUMN_NAME_VOTE_AVERAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRestClient.getMovieService();

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            poster_rv.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
        fetchMovies(sortByPopular);
        movieAdapter = new MovieAdapter(movieListener, movieList);
        poster_rv.setAdapter(movieAdapter);
        poster_rv.setHasFixedSize(true);


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
                movieAdapter.setMovieList(movieList);
                movieAdapter.notifyDataSetChanged();
                return true;

            case R.id.sort_by_ranking:
                fetchMovies(sortByHighestRated);
                movieAdapter.setMovieList(movieList);
                movieAdapter.notifyDataSetChanged();
                return true;
            case R.id.sort_by_favourites:
                MovieDbHelper dbHelper = new MovieDbHelper(this);
                db = dbHelper.getWritableDatabase();
                Cursor cursor = getAllMovies();
                //todo display fav movies
                favouriteMoviesFromDB = parseMoviesFromCursor(cursor);
                movieAdapter.setMovieList(favouriteMoviesFromDB);
                movieAdapter.notifyDataSetChanged();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private Cursor getAllMovies() {
        return db.query(MovieContract.MovieEntry.TABLE_NAME, null, null, null, null, null, MovieContract.MovieEntry.COLUMN_NAME_TITLE);
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
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        Log.d(TAG, "onCreateLoader");
        switch (loaderId){
            Uri movieQueryUri = MovieContract.MovieEntry.CONTENT_URI;
            String sortOrder = MovieContract.MovieEntry.COLUMN_NAME_POPULARITY + " ASC";
            String selection = MovieContract.MovieEntry.
//// TODO: 19.05.2017
            return new CursorLoader(this,
                    movieQueryUri,
                    DMOVIE_DETAILS_PROJECTION,
                    null,
                    null,
                    sortOrder);

            default:
                throw new RuntimeException("Loader Not Implemented: " + loaderId);
        }
        }
        return new MovieContentProvider.FavouriteMoviesCursorLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.d(TAG, "onLoadFinished");
        //todo in londonbike app
        //movieAdapter.changeCursor(data);
        movieAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset");
        //todo in londonbike app
        // movieAdapter.changeCursor(null);
        movieAdapter.swapCursor(null);
    }
}














































