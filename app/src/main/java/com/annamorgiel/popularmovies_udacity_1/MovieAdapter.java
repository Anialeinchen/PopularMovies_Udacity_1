package com.annamorgiel.popularmovies_udacity_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    //todo
    private List<MovieObject> movieList = new ArrayList<MovieObject>() {};

    final private GridItemClickListener mOnClickListener;

    //return null;?
    public MovieAdapter(GridItemClickListener mOnClickListener) {
        //this.movieList = movieList;
        this.mOnClickListener = mOnClickListener;
    }

    public void setMovieList(List<MovieObject> movies) {
        movieList = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.grid_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmidiately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        MovieViewHolder holder = new MovieViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ImageView gridItemPosterView;
        //gridItemPosterView = (ImageView) holder.posterImageView.findViewById(R.id.poster_iv);
        gridItemPosterView = holder.posterImageView;
        String posterPath = movieList.get(position).getPosterPath();
        Log.d(TAG, "onBindViewHolder: posterpath " + posterPath);

        String url = "http://image.tmdb.org/t/p/w185/";
        Picasso.with(holder.posterImageView.getContext())
                .load(url + posterPath)
                .into(gridItemPosterView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public interface GridItemClickListener {
        void onGridItemClick(int clickedItemIndex);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageView posterImageView;

        public MovieViewHolder(View view){
            super(view);
            posterImageView = (ImageView) view.findViewById(R.id.poster_iv);
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int clickedPostition;
            clickedPostition = getAdapterPosition();
            mOnClickListener.onGridItemClick(clickedPostition);
        }
    }
}
