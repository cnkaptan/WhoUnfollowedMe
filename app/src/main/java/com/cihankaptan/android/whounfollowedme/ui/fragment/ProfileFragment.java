package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.MainActivity;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    public static final String FOLLOWEDBY_LIST = "FOLLOWEDBY_LIST";
    public static final String FOLLOWS_LIST = "FOLLOWS_LIST";
    @InjectView(R.id.profilePhoto)
    ImageView profileImage;
    @InjectView(R.id.username)
    TextView userName;
    @InjectView(R.id.follows_button)
    Button follows;
    @InjectView(R.id.followedby_button)
    Button followedBy;
    private View view;
    private MainActivity activity;

    User user;
    ArrayList<User> followsList, followedByList;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        user = MySharedPrefs.loadObject("USER", User.class);
        followsList = (ArrayList<User>) MySharedPrefs.loadList(FOLLOWS_LIST, User.class);
        followedByList = (ArrayList<User>) MySharedPrefs.loadList(FOLLOWEDBY_LIST, User.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);

        Picasso.with(activity).load(user.getProfile_picture()).into(profileImage);
        userName.setText(user.getUsername());
        follows.setText("" + followsList.size());
        followedBy.setText("" + followedByList.size());

        followedBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(FollowedByFragment.newInstance());
            }
        });

        follows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(FollowsFragment.newInstance());
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }





}
