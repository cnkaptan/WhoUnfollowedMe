package com.cihankaptan.android.whounfollowedme.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.adapter.UserListAdapter;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.MainActivity;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FollowedByFragment extends Fragment implements Constans{

    @InjectView(R.id.reyclerView)
    RecyclerView reyclerView;
    private View view;
    private MainActivity activity;
    private ArrayList<User> followedByList;

    public static FollowedByFragment newInstance() {
        FollowedByFragment fragment = new FollowedByFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    public FollowedByFragment() {
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

        String json = MySharedPrefs.loadString(FOLLOWEDBY_LIST);
        followedByList = new GsonBuilder().create().fromJson(json,new TypeToken<List<User>>(){}.getType());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_followed_by, container, false);
        ButterKnife.inject(this, view);

        UserListAdapter userListAdapter = new UserListAdapter(followedByList,activity);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity);
        reyclerView.setLayoutManager(layoutManager);
        reyclerView.setAdapter(userListAdapter);
        activity.setTitle("Takip Edenler");
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
