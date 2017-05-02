package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class ApiResponse {
//changed total_movies to total results and also movies to results
    //giuhi
    @SerializedName("page")
    @Expose
    public Integer page;
    @SerializedName("results")
    @Expose
    public List<MovieObject> movies =  new ArrayList<MovieObject>();
    @SerializedName("total_results")
    @Expose
    public Integer totalMovieObjects;
    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

    public ApiResponse() {
    }

    public ApiResponse(Integer page, List<MovieObject> movies, Integer totalMovieObjects, Integer totalPages) {
        this.page = page;
        this.movies = movies;
        this.totalMovieObjects = totalMovieObjects;
        this.totalPages = totalPages;
    }

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
