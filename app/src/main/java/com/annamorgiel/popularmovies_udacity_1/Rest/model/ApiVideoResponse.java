package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Anna Morgiel on 10.05.2017.
 */

public class ApiVideoResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<VideoObject> videos = null;

    public final static Parcelable.Creator<ApiVideoResponse> CREATOR = new Creator<ApiVideoResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ApiVideoResponse createFromParcel(Parcel in) {
            ApiVideoResponse instance = new ApiVideoResponse();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.videos, (com.annamorgiel.popularmovies_udacity_1.Rest.model.VideoObject.class.getClassLoader()));
            return instance;
        }

        public ApiVideoResponse[] newArray(int size) {
            return (new ApiVideoResponse[size]);
        }

    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<VideoObject> getVideoObjects() {
        return videos;
    }

    public void setVideoObjects(List<VideoObject> videos) {
        this.videos = videos;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(videos);
    }

    public int describeContents() {
        return 0;
    }

}
