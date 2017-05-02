package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import org.parceler.Parcel;
import org.parceler.ParcelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */
@Parcel
public class MovieObject {

    @ParcelProperty("poster_path")
     String posterPath;
    
    @ParcelProperty("adult")
     Boolean adult;
    
    @ParcelProperty("overview")
     String overview;
    
    @ParcelProperty("release_date")
     String releaseDate;
    
    @ParcelProperty("genre_ids")
     List<Integer> genreIds =  new ArrayList<Integer>();
    
    @ParcelProperty("id")
     Integer id;
    
    @ParcelProperty("runtime")
     Integer runtime;
    
    @ParcelProperty("original_title")
     String originalTitle;
    
    @ParcelProperty("original_language")
     String originalLanguage;
    
    @ParcelProperty("title")
     String title;
    
    @ParcelProperty("backdrop_path")
     String backdropPath;
    
    @ParcelProperty("popularity")
     Double popularity;
    
    @ParcelProperty("vote_count")
     Integer voteCount;
    
    @ParcelProperty("video")
     Boolean video;
    
    @ParcelProperty("vote_average")
     Double voteAverage;
    
    public MovieObject(){}

    public MovieObject(String posterPath, Boolean adult, String overview, String releaseDate, List<Integer> genreIds,
                        Integer id, Integer runtime, String originalTitle, String originalLanguage, String title,
                        String backdropPath, Double popularity, Integer voteCount, Double voteAvarege, Boolean video){
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
        this.voteAverage = voteAvarege;
        this.video = video;

    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }
}

