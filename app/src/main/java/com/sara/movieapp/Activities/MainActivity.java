package com.sara.movieapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sara.movieapp.Fragments.DetailFragment;
import com.sara.movieapp.Fragments.MovieFragment;
import com.sara.movieapp.Models.Movie;
import com.sara.movieapp.R;

public class MainActivity extends AppCompatActivity implements MovieFragment.BunldeCallback {

    private boolean twopane = false;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main, new MovieFragment(), "Main Fragment List")
                    .commit();
        }
    }

    @Override
    public void onItemSelected(Movie movie) {
        if (twopane) {
            Bundle args = new Bundle();
            args.putSerializable(DetailFragment.DETAIL_URI, movie);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(DetailFragment.DETAIL_URI, movie);
            startActivity(intent);
        }
    }
}