package com.annamorgiel.popularmovies_udacity_1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();

    private List<MovieObject> movieList = new List<MovieObject>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<MovieObject> iterator() {
            return null;
        }

        @NonNull
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @NonNull
        @Override
        public <T> T[] toArray(@NonNull T[] ts) {
            return null;
        }

        @Override
        public boolean add(MovieObject movie) {
            return false;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends MovieObject> collection) {
            return false;
        }

        @Override
        public boolean addAll(int i, @NonNull Collection<? extends MovieObject> collection) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> collection) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public MovieObject get(int i) {
            return null;
        }

        @Override
        public MovieObject set(int i, MovieObject movie) {
            return null;
        }

        @Override
        public void add(int i, MovieObject movie) {

        }

        @Override
        public MovieObject remove(int i) {
            return null;
        }

        @Override
        public int indexOf(Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(Object o) {
            return 0;
        }

        @Override
        public ListIterator<MovieObject> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<MovieObject> listIterator(int i) {
            return null;
        }

        @NonNull
        @Override
        public List<MovieObject> subList(int i, int i1) {
            return null;
        }}
    ;

    final private GridItemClickListener mOnClickListener;

    public MovieAdapter(List<MovieObject> movieList, GridItemClickListener mOnClickListener) {
        this.movieList = movieList;
        this.mOnClickListener = mOnClickListener;
    }

    public void setMovieList(List<MovieObject> movies) {
        movieList = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.grid_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmidiately = false;

        View view = inflater.inflate(layoutIdForGridItem, parent, shouldAttachToParentImmidiately);
        MovieViewHolder holder = new MovieViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        ImageView gridItemPosterView;
        gridItemPosterView = (ImageView) holder.posterImageView.findViewById(R.id.poster_iv);
        String posterPath = movieList.get(position).getPosterPath();
        String url = "http://image.tmdb.org/t/p/w185/";
        //is it the right context?
        Picasso.with(gridItemPosterView.getContext()).load(url + posterPath).into(gridItemPosterView);
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
            //set onClickListener on posterImageView or View?
            posterImageView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int clickedPostition = getAdapterPosition();
            mOnClickListener.onGridItemClick(clickedPostition);
        }
    }
}
