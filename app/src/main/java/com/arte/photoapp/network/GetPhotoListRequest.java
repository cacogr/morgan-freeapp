package com.arte.photoapp.network;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.arte.photoapp.model.Photo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetPhotoListRequest {

    private static final String PHOTO_LIST_URL = "http://jsonplaceholder.typicode.com/photos";

    public interface Callbacks {
        void onGetPhotoListSuccess(List<Photo> photoList);

        void onGetPhotoListError();
    }

    private Context mContext;
    private Callbacks mCallbacks;

    public GetPhotoListRequest(Context context, Callbacks callbacks) {
        mContext = context;
        mCallbacks = callbacks;
    }

    public void execute() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, PHOTO_LIST_URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                List<Photo> photoList = new ArrayList<>();

                for (int i = 0; i < response.length(); i++) {
                    Photo photo = new Photo();
                    try {
                        JSONObject currentObject = response.getJSONObject(i);
                        photo.setId("" + currentObject.getInt("id"));
                        photo.setTitle(currentObject.getString("title"));
                        photo.setUrl(currentObject.getString("url"));
                        photo.setThumbnailUrl(currentObject.getString("thumbnailUrl"));
                    } catch (JSONException e) {
                        Log.e(GetPhotoListRequest.class.getSimpleName(), "Error deserializando JSON", e);
                        mCallbacks.onGetPhotoListError();
                        return;
                    }

                    photoList.add(photo);
                }

                mCallbacks.onGetPhotoListSuccess(photoList);
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
