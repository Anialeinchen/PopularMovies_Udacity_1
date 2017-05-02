package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class ApiResponse {
    //changed total_movies to total results and also movies to results
    @ParcelProperty("page")
    public Integer page;

    @ParcelProperty("results")
    public List<MovieObject> movies = new ArrayList<MovieObject>();

    @ParcelProperty("total_results")
    public Integer totalMovieObjects;

    @ParcelProperty("total_pages")
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
