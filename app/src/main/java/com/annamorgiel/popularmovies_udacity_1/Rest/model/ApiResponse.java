package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class ApiResponse {

    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("movies")
    @Expose
    private List<MovieObject> movies =  new ArrayList<MovieObject>();
    @SerializedName("total_movies")
    @Expose
    private Integer totalMovieObjects;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;

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

}
