package com.annamorgiel.popularmovies_udacity_1.Rest.service;

import retrofit2.Callback;
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
    @GET("/movie/{sortby}?api_key={apkiKey}")
    Call<ApiResponse> getMovies(@Path("sortby") String sortby, @Query("api_key") String apiKey);

    @GET("/{id}/movie/top_rated")
    Call<ApiResponse>  getMovieDetails(@Path("id") Integer id, @Query("api_key") String name);

}
