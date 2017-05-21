package com.annamorgiel.popularmovies_udacity_1.Rest.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by andrii.kovalchuk and Anna Morgiel on 20/05/2017.
 * choose the primary key and create getters and setters for the movie id
 */

public class FavoritesItem extends RealmObject {
    @PrimaryKey private Integer movieId;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
