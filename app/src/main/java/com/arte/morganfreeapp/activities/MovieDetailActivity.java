package com.arte.morganfreeapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.arte.morganfreeapp.R;
import com.arte.morganfreeapp.fragments.MovieDetailFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        setupActivity();
        setupFragment(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, MovieListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }

        Bundle fragmentArguments = new Bundle();
        fragmentArguments.putString(MovieDetailFragment.ARG_MOVIE_TITLE, getIntent().getStringExtra(MovieDetailFragment.ARG_MOVIE_TITLE));
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(fragmentArguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.photo_detail_container, fragment)
                .commit();

    }
}
