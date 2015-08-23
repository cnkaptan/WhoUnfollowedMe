package com.cihankaptan.android.whounfollowedme.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.ProfileActivity;
import com.cihankaptan.android.whounfollowedme.ui.activity.UserDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 15/07/15.
 */
public class UserListGridAdapter extends RecyclerView.Adapter<UserListGridAdapter.UserListGridHolder> {


    private static ProfileActivity context;
    private ArrayList<User> users;
    private User user;

    public UserListGridAdapter(ArrayList<User> users,Activity context){
        this.users = users;
        this.context = (ProfileActivity)context;

    }

    @Override
    public UserListGridHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.userlist_item_grid,viewGroup,false);
        UserListGridHolder userViewHolder = new UserListGridHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(UserListGridHolder userViewHolder, int position) {
        userViewHolder.bindUser(users.get(position));
    }



    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserListGridHolder extends RecyclerView.ViewHolder{

        public TextView fullName;
        public TextView userName;
        public ImageView profilePhoto;
        public CardView cardView;

        public UserListGridHolder(View itemView) {
            super(itemView);

            fullName = (TextView) itemView.findViewById(R.id.full_name);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
            cardView = (CardView)itemView.findViewById(R.id.cardView);
        }

        public void bindUser(final User user) {
            fullName.setText(user.getFull_name());
            userName.setText(user.getUsername());
            Picasso.with(context).load(user.getProfile_picture()).into(profilePhoto);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, UserDetailActivity.class);
                    intent.putExtra("user",user);
                    context.startActivity(intent);
                }
            });
        }
    }
}
