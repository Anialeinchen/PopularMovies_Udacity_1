package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Morgiel on 02.05.2017.
 */

public class MovieObject2 {

    public MovieObject2(){
    }

    public MovieObject2(String posterPath, Boolean adult, String overview, String releaseDate, List<Integer> genreIds,
                        Integer id, Integer runtime, String originalTitle, String originalLanguage, String title,
                        String backdropPath, Double popularity, Integer voteCount, Boolean video){
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.runtime = runtime;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;

    }
    ("poster_path")
    
     String posterPath;
    ("adult")
    
     Boolean adult;
    ("overview")
    
     String overview;
    ("release_date")
    
     String releaseDate;
    ("genre_ids")
    
     List<Integer> genreIds =  new ArrayList<Integer>();
    ("id")
    
     Integer id;
    ("runtime")
    
     Integer runtime;
    ("original_title")
    
     String originalTitle;
    ("original_language")
    
     String originalLanguage;
    ("title")
    
     String title;
    ("backdrop_path")
    
     String backdropPath;
    ("popularity")
    
     Double popularity;
    ("vote_count")
    
     Integer voteCount;
    ("video")
    
     Boolean video;
    ("vote_average")
    
}
