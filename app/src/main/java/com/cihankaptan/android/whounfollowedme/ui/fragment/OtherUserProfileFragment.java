package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.MainActivity;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherUserProfileFragment extends Fragment implements Constans{
    MainActivity activity;
    User user;
    @InjectView(R.id.profilePhoto)
    ImageView profilePhoto;
    @InjectView(R.id.full_name)
    FontTextView fullName;
    @InjectView(R.id.user_name)
    FontTextView userName;

    public static OtherUserProfileFragment newInstance(User user) {
        OtherUserProfileFragment fragment = new OtherUserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    public OtherUserProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity)activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_user_profile, container, false);
        ButterKnife.inject(this, view);

        activity.instagramApi.getRecentPics(user.getId(), MySharedPrefs.loadString(ACCESS_TOKEN), new Callback<MediaResponse>() {
            @Override
            public void success(MediaResponse mediaResponse, Response response) {

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity.setTitle(user.getFull_name());
        Picasso.with(activity).load(user.getProfile_picture()).into(profilePhoto);
        fullName.setText(user.getFull_name());
        userName.setText(user.getUsername());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
