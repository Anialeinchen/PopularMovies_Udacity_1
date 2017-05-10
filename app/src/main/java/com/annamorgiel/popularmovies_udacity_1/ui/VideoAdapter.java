package com.annamorgiel.popularmovies_udacity_1.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject;

import java.util.ArrayList;

/**
 * Created by Anna Morgiel on 10.05.2017.
 */

public class VideoAdapter extends ArrayAdapter<VideoObject> implements View.OnClickListener{

    private ArrayList<VideoObject> videoList;
    Context mContext;
    String videoKey;
    int layoutResourceId;


    // View lookup cache
    private static class ViewHolder {
     String key;
    }

    public VideoAdapter(ArrayList<VideoObject> data, Context context, int layoutResourceId) {
        super(context, layoutResourceId, data);
        this.videoList = data;
        this.mContext=context;
        this.layoutResourceId = layoutResourceId;

    }

    @Override
    public void onClick(View v) {
        watchYoutubeVideo(videoKey);
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoObject videoObject = getItem(position);
        ViewHolder viewHolder;

        if(convertView==null){
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }
        VideoObject video = videoList.get(position);
        videoKey = video.getKey();
        return convertView;
        }

}