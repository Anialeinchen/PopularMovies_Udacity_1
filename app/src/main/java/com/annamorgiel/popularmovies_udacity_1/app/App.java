package com.annamorgiel.popularmovies_udacity_1.app;

import android.app.Application;

import com.annamorgiel.popularmovies_udacity_1.Rest.RestClient;

/**
 * Created by Anna Morgiel on 23.04.2017.
 */

public class App extends Application {
    private static RestClient restClient;

    @Override
    public void onCreate()
    {
        super.onCreate();

        restClient = new RestClient();
    }

    public static RestClient getRestClient()
    {
        return restClient;
    }

}
