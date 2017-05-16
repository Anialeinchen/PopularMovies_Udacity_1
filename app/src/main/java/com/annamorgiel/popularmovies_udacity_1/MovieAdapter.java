package com.annamorgiel.popularmovies_udacity_1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.annamorgiel.popularmovies_udacity_1.ui.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private int movieCount;
    private List<MovieObject> movieList = new ArrayList<MovieObject>() {
    };
    private View.OnClickListener mOnClickListener;

    public MovieAdapter(View.OnClickListener mOnClickListener, List<MovieObject> mList) {
        this.mOnClickListener = mOnClickListener;
        this.movieCount = movieList.size();
        this.movieList = mList;
    }


    public void setMovieList(List<MovieObject> movies) {
        this.movieList = movies;
        this.movieCount = movieList.size();
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.grid_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        view.setOnClickListener(mOnClickListener);
        MovieViewHolder holder = new MovieViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {
        ImageView gridItemPosterView;
        gridItemPosterView = holder.posterImageView;
        String posterPath = movieList.get(position).getPosterPath();
        Log.d(TAG, "onBindViewHolder: posterpath " + posterPath);

        String url = "http://image.tmdb.org/t/p/w185/";
        Picasso.with(holder.posterImageView.getContext())
                .load(url + posterPath)
                .placeholder(R.drawable.loading_poster)
                .error(R.drawable.ic_alert_circle)
                .into(gridItemPosterView);
    }

    @Override
    public int getItemCount() {
        return movieCount;
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final ImageView posterImageView;
        public static final String movieId = "movieId";

        public MovieViewHolder(View view) {
            super(view);
            posterImageView = (ImageView) view.findViewById(R.id.poster_iv);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Class destinationClass = DetailActivity.class;
            Integer adapterPosition = getAdapterPosition();
            Integer movieIdInt = movieList.get(adapterPosition).getId();
            Intent intentToStartDetailActivity = new Intent(view.getContext(), destinationClass);
            //done? delete string literals -> public static final
            //create util class?
            intentToStartDetailActivity.putExtra(movieId, movieIdInt);
            view.getContext().startActivity(intentToStartDetailActivity);
        }
    }
}
