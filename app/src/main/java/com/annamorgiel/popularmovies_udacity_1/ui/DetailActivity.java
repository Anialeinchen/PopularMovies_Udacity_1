package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.annamorgiel.popularmovies_udacity_1.R;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiReviewResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiVideoResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.FavoritesItem;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ReviewObject;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject;
import com.annamorgiel.popularmovies_udacity_1.ReviewAdapter;
import com.annamorgiel.popularmovies_udacity_1.VideoAdapter;
import com.annamorgiel.popularmovies_udacity_1.app.App;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.annamorgiel.popularmovies_udacity_1.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by Anna Morgiel on 23.04.2017.
 * <p>
 * we fetch the intent with a movie id in order to display the movie details.
 * Retrofit performs a network request and fetches the reviews, trailers and movie details.
 * The Button allows us to save a movie into a local db.
 * Clicking on the trailer field opens YouTube or a web browser with the trailer content.
 */

public class DetailActivity extends Activity {

    public static final int TAG_MOVIE_ADDED_TO_FAV = 0;
    public static final int TAG_MOVIE_REMOVED_FROM_FAVORITES = 1;

    @BindView(R.id.detail_poster_iv)
    ImageView posterDetail;
    @BindView(R.id.detail_movie_title)
    TextView title;
    @BindView(R.id.detail_release_date_tv)
    TextView releaseDate;
    @BindView(R.id.detail_movie_length_tv)
    TextView length;
    @BindView(R.id.detail_ranking_tv)
    TextView ranking;
    @BindView(R.id.detail_movie_description)
    TextView desc;
    @BindView(R.id.rv_videos)
    RecyclerView videos_rv;
    @BindView(R.id.rv_reviews)
    RecyclerView reviews_rv;
    @BindView(R.id.detail_favorites_button)
    Button addMovieToFavourites;

    private View.OnClickListener favButtonListener;
    private MovieObject movie;
    private Integer movieId = null;
    private String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private String posterPath;
    private VideoAdapter videoAdapter;
    private List<VideoObject> videoList;
    private View.OnClickListener videoListener;
    private List<ReviewObject> reviewList;
    private ReviewAdapter reviewAdapter;
    private View.OnClickListener reviewListener;
    //realmComponents 5
    private Realm realmInstance;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //butterknife bindviews
        ButterKnife.bind(this);
        //realmComponents 6
        initRealm();

        //fetch id of a movie to display it's details, default id = 0
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("movieId")) {
            movieId = intentThatStartedThisActivity.getIntExtra("movieId", 0);
        }
        fetchMovieDetails(movieId);
        initViews();
    }

    /**
     * set the video adapter, set the reviews , pollute both with data
     */
    private void initViews() {
        videos_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        videoAdapter = new VideoAdapter(videoListener);
        videos_rv.setAdapter(videoAdapter);
        videos_rv.setHasFixedSize(true);
        fetchVideos(movieId);

        reviews_rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        reviewAdapter = new ReviewAdapter(reviewListener);
        reviews_rv.setAdapter(reviewAdapter);
        reviews_rv.setHasFixedSize(true);
        fetchReviews(movieId);

        addMovieToFavourites.setTag(1);
        addMovieToFavourites.setText(R.string.mark_as_favorite);
        addMovieToFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int status = (Integer) v.getTag();
                if (status == 1) {
                    addMovieToFavourites();
                    movieAddedToFavorites();
                } else {
                    removeMovieFromFavourites();
                    movieNotAddedToFavorites();
                }
            }
        });
    }

    private void movieNotAddedToFavorites() {
        addMovieToFavourites.setText(R.string.mark_as_favorite);
        addMovieToFavourites.setTag(TAG_MOVIE_REMOVED_FROM_FAVORITES);
    }

    private void movieAddedToFavorites() {
        addMovieToFavourites.setText(R.string.remove_from_favorties);
        addMovieToFavourites.setTag(TAG_MOVIE_ADDED_TO_FAV);
    }

    private void initRealm() {
        Realm.init(this);
        realmInstance = Realm.getDefaultInstance();
    }

    public void addMovieToFavourites() {
        final FavoritesItem favoritesItem = new FavoritesItem();
        favoritesItem.setMovieId(movieId);
        //realmComponents 7
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realmInstance.copyToRealmOrUpdate(favoritesItem);
            }
        });
        //realmComponents 8
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(FavoritesItem.class).findAll();
            }
        });
    }

    private void checkIfAddedToFavorites() {
        //realmComponents 9
        final FavoritesItem movieId = realmInstance.where(FavoritesItem.class)
                .equalTo("movieId", this.movieId)
                .findFirst();

        if (movieId != null && movieId.getMovieId() != null) {
            movieAddedToFavorites();
        } else {
            movieNotAddedToFavorites();
        }
    }

    /**
     *
     * @param id is crucial for the network request.
     * we set the movie details, Picasso loads movie posters, displaying a round loading icon while loading
     * or error icon if there is no internet connection
     */
    private void fetchMovieDetails(Integer id) {
        final Call movieDetailCall = App.getRestClient().getMovieService().getMovieDetails(id, THE_MOVIE_DB_API_KEY);
        movieDetailCall.enqueue(new Callback<MovieObject>() {
            @Override
            public void onResponse(Call<MovieObject> call, Response<MovieObject> response) {
                // get raw response

                movie = response.body();
                title.setText(movie.getTitle());
                releaseDate.setText(movie.getReleaseDate());
                desc.setText(movie.getOverview());
                length.setText(movie.getRuntime().toString() + " min");
                ranking.setText(movie.getVoteAverage().toString());
                posterPath = movie.getPosterPath();

                Picasso.with(getApplicationContext())
                        .load(BASE_POSTER_URL + posterPath)
                        .placeholder(R.drawable.loading_poster)
                        .error(R.drawable.ic_alert_circle)
                        .into(posterDetail);

                checkIfAddedToFavorites();
            }

            @Override
            public void onFailure(Call<MovieObject> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Please check your internet connection, buddy!",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * fetch trailer links
     * @param id from a movie
     */
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

    /**
     *
     * @param id for revies of a movie with this id
     */
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

    private void removeMovieFromFavourites() {
        final FavoritesItem favoritesItem = realmInstance.where(FavoritesItem.class).equalTo("movieId", this.movieId).findFirst();
        realmInstance.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                favoritesItem.deleteFromRealm();
            }
        });
    }
}

