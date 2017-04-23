package com.annamorgiel.popularmovies_udacity_1.ui;

import android.graphics.Movie;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<Movie> movieList;

    final private GridItemClickListener mOnClickListener;

    public MovieAdapter(List<Movie> movieList, GridItemClickListener mOnClickListener) {
        this.movieList = movieList;
        this.mOnClickListener = mOnClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }

    private class MovieViewHolder {
    }
}
