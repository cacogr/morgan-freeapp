package com.arte.photoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.arte.photoapp.R;
import com.arte.photoapp.model.Photo;
import com.arte.photoapp.network.RequestQueueManager;

import java.util.List;

public class PhotoRecyclerViewAdapter extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.ViewHolder> {

    public interface Events {
        void onPhotoClicked(Photo photo);
    }

    private final List<Photo> mPhotoList;
    private Events mEvents;
    private Context mContext;

    public PhotoRecyclerViewAdapter(List<Photo> items, Context context, Events events) {
        mPhotoList = items;
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
        final Photo photo = mPhotoList.get(position);
        holder.mTitle.setText(photo.getTitle());
        holder.mThumbnail.setImageUrl(photo.getThumbnailUrl(), RequestQueueManager.getInstance(mContext).getImageLoader());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.onPhotoClicked(photo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPhotoList.size();
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
