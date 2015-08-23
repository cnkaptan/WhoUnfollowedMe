package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.adapter.UserListGridAdapter;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.ProfileActivity;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FollowsFragment extends Fragment implements Constans {

    @InjectView(R.id.reyclerView)
    RecyclerView reyclerView;
    private View view;
    private ProfileActivity activity;
    private ArrayList<User> followsList;

    public static FollowsFragment newInstance() {
        FollowsFragment fragment = new FollowsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public FollowsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ProfileActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        String json = MySharedPrefs.loadString(FOLLOWS_LIST);
        followsList = new GsonBuilder().create().fromJson(json, new TypeToken<List<User>>() {
        }.getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_follows, container, false);
        ButterKnife.inject(this, view);

        UserListGridAdapter userListAdapter = new UserListGridAdapter(followsList, activity);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity,2);
        reyclerView.setLayoutManager(layoutManager);
        reyclerView.setAdapter(userListAdapter);
        activity.setTitle(getResources().getString(R.string.follows));
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
