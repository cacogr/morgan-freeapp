package com.arte.morganfreeapp.network;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arte.morganfreeapp.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetMovieListRequest {

    private static final String MOVIE_LIST_URL = "http://netflixroulette.net/api/api.php?actor=Morgan%20Freeman";

    public interface Callbacks {
        void onGetPhotoListSuccess(List<Movie> movieList);

        void onGetPhotoListError();
    }

    private Context mContext;
    private Callbacks mCallbacks;

    public GetMovieListRequest(Context context, Callbacks callbacks) {
        mContext = context;
        mCallbacks = callbacks;
    }

    public void execute() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, MOVIE_LIST_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                List<Movie> movieList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    Movie movie = new Movie();
                    try {
                        JSONObject currentObject = response.getJSONObject(i);
                        movie.setId("" + currentObject.getInt("show_id"));
                        movie.setTitle(currentObject.getString("show_title"));
                        movie.setUrl(currentObject.getString("poster"));
                        movie.setThumbnailUrl(currentObject.getString("poster"));
                    } catch (JSONException e) {
                        Log.e(GetMovieListRequest.class.getSimpleName(), "Error deserializando JSON", e);
                        mCallbacks.onGetPhotoListError();
                        return;
                    }

                    movieList.add(movie);
                }

                mCallbacks.onGetPhotoListSuccess(movieList);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mCallbacks.onGetPhotoListError();
            }
        });

        RequestQueueManager.getInstance(mContext).addToRequestQueue(request);
    }
}
