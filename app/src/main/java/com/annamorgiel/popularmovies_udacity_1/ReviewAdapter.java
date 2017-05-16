package com.annamorgiel.popularmovies_udacity_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.ReviewObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;

/**
 * Created by Anna Morgiel on 15.05.2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private static final String TAG = ReviewAdapter.class.getSimpleName();

    private List<ReviewObject> reviewList = new ArrayList<ReviewObject>() {
    };
    private View.OnClickListener mOnClickListener;

    public ReviewAdapter(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setReviewList(List<ReviewObject> reviews) {
        reviewList = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item_view_review;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, parent, false);
        view.setOnClickListener(mOnClickListener);
        ReviewViewHolder holder = new ReviewViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final ReviewViewHolder holder, int position) {

        holder.author_tv.setText(reviewList.get(position).getAuthor());
        holder.content_tv.setText(reviewList.get(position).getContent());
        holder.url_tv.setText(reviewList.get(position).getUrl());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView author_tv;
        public final TextView content_tv;
        public final TextView url_tv;

        public ReviewViewHolder(View view) {
            super(view);
            author_tv = (TextView) view.findViewById(R.id.review_author);
            content_tv = (TextView) view.findViewById(R.id.review_content);
            url_tv = (TextView) view.findViewById(R.id.review_url);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Integer adapterPosition = getAdapterPosition();
            String url = reviewList.get(adapterPosition).getUrl();
            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + key)));
        }
    }

}
