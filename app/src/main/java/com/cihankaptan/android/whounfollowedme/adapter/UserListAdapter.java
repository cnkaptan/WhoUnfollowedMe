package com.cihankaptan.android.whounfollowedme.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.MainActivity;
import com.cihankaptan.android.whounfollowedme.ui.fragment.OtherUserProfileFragment;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 15/07/15.
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder>{

    private static MainActivity activity;
    private ArrayList<User> users;


    public UserListAdapter(ArrayList<User> users,MainActivity context){
        this.users = users;
        UserListAdapter.activity = context;

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

        public FontTextView fullName;
        public FontTextView userName;
        public ImageView profilePhoto;
        public CardView cardView;

        public UserViewHolder(View itemView) {
            super(itemView);

            fullName = (FontTextView) itemView.findViewById(R.id.full_name);
            userName = (FontTextView) itemView.findViewById(R.id.user_name);
            profilePhoto = (ImageView) itemView.findViewById(R.id.profilePhoto);
            cardView = (CardView) itemView.findViewById(R.id.cardView);

        }

        public void bindUser(final User user) {
            fullName.setText(user.getFull_name());
            userName.setText(user.getUsername());
            Picasso.with(activity).load(user.getProfile_picture()).into(profilePhoto);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Uri uri = Uri.parse("http://instagram.com/"+user.getUsername());
//                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
//
//                    likeIng.setPackage("com.instagram.android");
//
//                    try {
//                        activity.startActivity(likeIng);
//                    } catch (ActivityNotFoundException e) {
//                        activity.startActivity(new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("http://instagram.com/"+user.getUsername())));
//                    }

                    activity.replaceFragment(OtherUserProfileFragment.newInstance(user));
                }
            });

        }
    }
}
