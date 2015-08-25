package com.cihankaptan.android.whounfollowedme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.Media;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.squareup.picasso.Picasso;

/**
 * Created by cnkaptan on 17/08/15.
 */
public class OtherUserListAdapter extends RecyclerView.Adapter<OtherUserListAdapter.OtherUserViewHolder> {

    private MediaResponse mediaResponse;
    private SendMediaListener sendMediaListener;

    public OtherUserListAdapter(MediaResponse mediaResponse){
        this.mediaResponse = mediaResponse;
    }

    @Override
    public OtherUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item_grid,parent,false);
        OtherUserViewHolder viewHolder = new OtherUserViewHolder(view,parent.getContext());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OtherUserViewHolder holder, final int position) {
        holder.bindView(mediaResponse.getMedias().get(position));
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMediaListener.sendMedia(mediaResponse.getMedias().get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mediaResponse.getMedias().size();
    }

    public static class OtherUserViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        static Context context;
        public OtherUserViewHolder(View itemView,Context context) {
            super(itemView);

            imageView = (ImageView)itemView.findViewById(R.id.photo);
            this.context = context;
        }

        public void bindView(final Media media){
            Picasso.with(context).load(media.getImages().getStandart_resulation().getUrl()).into(imageView);

        }
    }


    public interface SendMediaListener{
        void sendMedia(Media media);
    }

    public void setSendMediaListener(SendMediaListener sendMediaListener){
        this.sendMediaListener = sendMediaListener;
    }

}
