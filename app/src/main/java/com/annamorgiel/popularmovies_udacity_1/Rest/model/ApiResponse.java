package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ApiResponse implements Parcelable{
//changed total_movies to total results and also movies to results
    //giuhi
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieObject> movies =  new ArrayList<MovieObject>();
    @SerializedName("total_results")
    @Expose
    private Integer totalMovieObjects;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

    protected ApiResponse() {
    }

    public static final Creator<ApiResponse> CREATOR = new Creator<ApiResponse>() {
        @Override
        public ApiResponse createFromParcel(android.os.Parcel in) {
            ApiResponse instance = new ApiResponse();
            instance.page = ((Integer) in.readValue((Integer.class.getClassLoader())));
            in.readList(instance.movies, (com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject.class.getClassLoader()));
            instance.totalMovieObjects = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.totalPages = ((Integer) in.readValue((Integer.class.getClassLoader())));
            return instance;
        }

        @Override
        public ApiResponse[] newArray(int size) {
            return new ApiResponse[size];
        }
    };

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public List<MovieObject> getMovieObjects() {
        return movies;
    }

    public void setMovieObjects(List<MovieObject> movies) {
        this.movies = movies;
    }

    public Integer getTotalMovieObjects() {
        return totalMovieObjects;
    }

    public void setTotalMovieObjects(Integer totalMovieObjects) {
        this.totalMovieObjects = totalMovieObjects;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(movies);
        dest.writeValue(totalMovieObjects);
        dest.writeValue(totalPages);
    }
}
