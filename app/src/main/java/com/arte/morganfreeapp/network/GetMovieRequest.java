package com.arte.morganfreeapp.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.arte.morganfreeapp.model.Movie;

import org.json.JSONException;
import org.json.JSONObject;

public class GetMovieRequest {

    private static final String GET_MOVIE_URL = "http://netflixroulette.net/api/api.php?title=";

    public interface Callbacks {
        void onGetPhotoSuccess(Movie movie);

        void onGetPhotoError();
    }

    private Context mContext;
    private Callbacks mCallbacks;
    private String mId;

    public GetMovieRequest(Context context, Callbacks callbacks, String id) {
        mContext = context;
        mCallbacks = callbacks;
        mId = id;
    }

    public void execute() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getGetMovieUrl(mId), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Movie movie = new Movie();
                try {
                    movie.setId("" + response.getInt("show_id"));
                    movie.setTitle(response.getString("show_title"));
                    movie.setUrl(response.getString("poster"));
                    movie.setThumbnailUrl(response.getString("poster"));
                    mCallbacks.onGetPhotoSuccess(movie);
                } catch (JSONException e) {
                    Log.e(GetMovieListRequest.class.getSimpleName(), "Error deserializando JSON", e);
                    mCallbacks.onGetPhotoError();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mCallbacks.onGetPhotoError();
            }
        });

        RequestQueueManager.getInstance(mContext).addToRequestQueue(request);
    }

    private String getGetMovieUrl(String id) {
        return GET_MOVIE_URL + id;
    }
}
