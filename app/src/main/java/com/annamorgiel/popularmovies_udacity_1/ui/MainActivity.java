package com.annamorgiel.popularmovies_udacity_1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.annamorgiel.popularmovies_udacity_1.R;

public class MainActivity extends AppCompatActivity {

    private RecyclerView poster_rv;
    private MovieAdapter adapter;
    private static final int NUM_GRID_ITEM = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find RecyclerView and set GridLayoutManager to handle ViewHolders in a grid
        poster_rv = (RecyclerView) findViewById(R.id.rv_movies);
        GridLayoutManager layoutManager = new GridLayoutManager(this,NUM_GRID_ITEM);
        poster_rv.setLayoutManager(layoutManager);

        adapter = new MovieAdapter();
    }
}
