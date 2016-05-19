package com.arte.morganfreeapp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.arte.morganfreeapp.R;
import com.arte.morganfreeapp.model.Movie;
import com.arte.morganfreeapp.network.GetMovieRequest;
import com.arte.morganfreeapp.network.RequestQueueManager;

public class MovieDetailFragment extends Fragment implements GetMovieRequest.Callbacks {

    public static final String ARG_MOVIE_TITLE = "movie_title";

    private Movie mMovie;
    private NetworkImageView mImage;
    private ProgressDialog mProgressDialog;

    public MovieDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_MOVIE_TITLE)) {
            String photoId = getArguments().getString(ARG_MOVIE_TITLE);
            GetMovieRequest request = new GetMovieRequest(getActivity(), this, photoId);
            request.execute();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(getString(R.string.photo_detail_loading));
        mProgressDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mProgressDialog.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.photo_detail, container, false);

        mImage = (NetworkImageView) rootView.findViewById(R.id.photo_image);
        if (mMovie != null) {
            loadPhotoDetails(mMovie);
        }

        return rootView;
    }

    private void loadPhotoDetails(Movie movie) {
        mMovie = movie;
        Activity activity = this.getActivity();
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(mMovie.getTitle());
        }
        mImage.setImageUrl(movie.getUrl(), RequestQueueManager.getInstance(activity).getImageLoader());
    }

    @Override
    public void onGetPhotoSuccess(Movie movie) {
        loadPhotoDetails(movie);
        mProgressDialog.hide();
    }

    @Override
    public void onGetPhotoError() {
        mProgressDialog.hide();
        Toast.makeText(getActivity(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show();
    }
}
