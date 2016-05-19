package com.arte.morganfreeapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.arte.morganfreeapp.R;
import com.arte.morganfreeapp.adapters.MovieRecyclerViewAdapter;
import com.arte.morganfreeapp.fragments.MovieDetailFragment;
import com.arte.morganfreeapp.model.Movie;
import com.arte.morganfreeapp.network.GetMovieListRequest;

import java.util.ArrayList;
import java.util.List;

public class MovieListActivity extends AppCompatActivity implements MovieRecyclerViewAdapter.Events, GetMovieListRequest.Callbacks {

    private boolean mTwoPane;
    private List<Movie> mMovieList = new ArrayList<>();
    private MovieRecyclerViewAdapter mAdapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        setupActivity();
        loadPhotos();
    }

    @Override
    public void onPhotoClicked(Movie movie) {
        if (mTwoPane) {
            Bundle fragmentArguments = new Bundle();
            fragmentArguments.putString(MovieDetailFragment.ARG_MOVIE_TITLE, movie.getTitle());
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(fragmentArguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.photo_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra(MovieDetailFragment.ARG_MOVIE_TITLE, movie.getTitle());
            startActivity(intent);
        }
    }

    private void setupActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.photo_list);
        assert recyclerView != null;
        mAdapter = new MovieRecyclerViewAdapter(mMovieList, this, this);
        recyclerView.setAdapter(mAdapter);

        if (findViewById(R.id.photo_detail_container) != null) {
            mTwoPane = true;
        }

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.movie_detail_loading));
        mProgressDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }


    private void loadPhotos() {
        GetMovieListRequest request = new GetMovieListRequest(this, this);
        request.execute();
    }

    @Override
    public void onGetPhotoListSuccess(List<Movie> movieList) {
        mProgressDialog.hide();
        mMovieList.addAll(movieList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onGetPhotoListError() {
        mProgressDialog.hide();
    }
}
