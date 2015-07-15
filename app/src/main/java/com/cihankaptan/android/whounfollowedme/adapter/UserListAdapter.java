package com.cihankaptan.android.whounfollowedme.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 15/07/15.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private static Context context;
    private ArrayList<User> users;
    private User user;

    public UserListAdapter(ArrayList<User> users,Context context){
        this.users = users;
        UserListAdapter.context = context;

    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_item,viewGroup,false);
        UserViewHolder userViewHolder = new UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserViewHolder userViewHolder, int position) {
        userViewHolder.bindUser(users.get(position));
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder{

        public TextView fullName;
        public TextView userName;
        public ImageView profilePhoto;

        public UserViewHolder(View itemView) {
            super(itemView);

            fullName = (TextView) itemView.findViewById(R.id.full_name);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
        }

        public void bindUser(User user) {
            fullName.setText(user.getFull_name());
            userName.setText(user.getUsername());
            Picasso.with(context).load(user.getProfile_picture()).into(profilePhoto);
        }
    }
}
