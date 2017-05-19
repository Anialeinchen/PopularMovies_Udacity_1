package com.annamorgiel.popularmovies_udacity_1;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Morgiel on 10.05.2017.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private static final String TAG = VideoAdapter.class.getSimpleName();

    private List<VideoObject> videoList = new ArrayList<VideoObject>() {
    };
    private View.OnClickListener mOnClickListener;

    public VideoAdapter(View.OnClickListener mOnClickListener) {
        this.mOnClickListener = mOnClickListener;
    }

    public void setVideoList(List<VideoObject> videos) {
        videoList = videos;
        notifyDataSetChanged();
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForGridItem = R.layout.list_item_view;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForGridItem, parent, false);
        view.setOnClickListener(mOnClickListener);
        VideoViewHolder holder = new VideoViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final VideoViewHolder holder, int position) {
        TextView tv;
        tv = holder.textView;
        tv.setText("Trailer " + position);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView textView;
        public final Button button;

        public VideoViewHolder(View view) {
            super(view);
            button = (Button) view.findViewById(R.id.trailer_play_button);
            textView = (TextView) view.findViewById(R.id.trailer1_tv);
            view.setOnClickListener(this);
        }

        public void onClick(View view) {
            Integer adapterPosition = getAdapterPosition();
            String key = videoList.get(adapterPosition).getKey();
            String url = "https://www.youtube.com/watch?v=";

            /*Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("www.youtube.com")
                    .appendPath("watch")
                    .appendQueryParameter("v", key);
            String myUrl = builder.build().toString();*/

            view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url + key)));
        }
    }
}