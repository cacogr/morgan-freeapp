package com.arte.morganfreeapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.arte.morganfreeapp.R;
import com.arte.morganfreeapp.model.Movie;
import com.arte.morganfreeapp.network.RequestQueueManager;

import java.util.List;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    public interface Events {
        void onPhotoClicked(Movie movie);
    }

    private final List<Movie> mMovieList;
    private Events mEvents;
    private Context mContext;

    public MovieRecyclerViewAdapter(List<Movie> items, Context context, Events events) {
        mMovieList = items;
        mContext = context;
        mEvents = events;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Movie movie = mMovieList.get(position);
        holder.mTitle.setText(movie.getTitle());
        holder.mThumbnail.setImageUrl(movie.getThumbnailUrl(), RequestQueueManager.getInstance(mContext).getImageLoader());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.onPhotoClicked(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final NetworkImageView mThumbnail;
        public final TextView mTitle;

        public ViewHolder(View view) {
            super(view);
            mThumbnail = (NetworkImageView) view.findViewById(R.id.photo_thumbnail);
            mTitle = (TextView) view.findViewById(R.id.photo_title);
        }
    }
}
