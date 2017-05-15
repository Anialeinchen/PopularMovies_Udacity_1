package com.annamorgiel.popularmovies_udacity_1.Rest.service;

import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiReviewResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.ApiVideoResponse;
import com.annamorgiel.popularmovies_udacity_1.Rest.model.MovieObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public interface MovieService {

    //No Callback as third parameter because we use retrofit 2
    //we get the list of movies

    /**
     *
     * @param sortby [popular] [new movies] replaces static parameter in network request
     * @param apiKey is for dynamic parameters, queries the result with =? apiKey
     * @return
     */
    @GET("movie/{sortby}")
    Call<ApiResponse> getMovies(@Path("sortby") String sortby, @Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieObject> getMovieDetails(@Path("id") Integer id, @Query("api_key") String name);

    @GET("movie/{id}/videos")
    Call<ApiVideoResponse> getVideos(@Path("id") Integer id, @Query("api_key") String name);

    @GET("movie/{id}/reviews")
    Call<ApiReviewResponse> getReviews(@Path("id") Integer id, @Query("api_key") String name);

}
