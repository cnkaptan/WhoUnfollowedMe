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
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GeneralListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GeneralListFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    @InjectView(R.id.reyclerView)
    RecyclerView reyclerView;
    private String mParam1;
    private ProfileActivity activity;
    private ArrayList<User> userArrayList;


    public static GeneralListFragment newInstance(String param1) {
        GeneralListFragment fragment = new GeneralListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public GeneralListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (ProfileActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }

        userArrayList = new GsonBuilder().create().fromJson(MySharedPrefs.loadString(mParam1),
                new TypeToken<List<User>>(){}.getType());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_general_list, container, false);
        ButterKnife.inject(this, view);


        UserListGridAdapter userListAdapter = new UserListGridAdapter(userArrayList, activity);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity,2);
        reyclerView.setLayoutManager(layoutManager);
        reyclerView.setAdapter(userListAdapter);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


}
