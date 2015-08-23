package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.adapter.OtherUserListAdapter;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.activity.ProfileActivity;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.ListUtil;
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
public class ProfileFragment extends Fragment implements Constans {
    private static final String TAG = ProfileFragment.class.getSimpleName();
    @InjectView(R.id.profilePhoto)
    ImageView profileImage;
    @InjectView(R.id.username)
    TextView userName;
    @InjectView(R.id.follows_button)
    Button follows;
    @InjectView(R.id.followedby_button)
    Button followedBy;
    @InjectView(R.id.you_unfollow)
    Button youUnfollow;
    @InjectView(R.id.unfollow_you)
    Button unfollowYou;
    @InjectView(R.id.reyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    private View view;
    private ProfileActivity activity;

    User user;
    ArrayList<User> followsList, followedByList, differenceFollows, differenceFollowedBy;
    private OtherUserListAdapter adapter;

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
        this.activity = (ProfileActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        user = MySharedPrefs.loadObject("USER", User.class);
        followsList = (ArrayList<User>) MySharedPrefs.loadList(FOLLOWS_LIST, User.class);
        followedByList = (ArrayList<User>) MySharedPrefs.loadList(FOLLOWEDBY_LIST, User.class);

        differenceFollows = (ArrayList<User>) ListUtil.difference(followsList, followedByList);
        MySharedPrefs.saveList(YOU_UNFOLLOWED, differenceFollows);
        Log.e(TAG, String.valueOf(differenceFollows.size()));
        differenceFollowedBy = (ArrayList<User>) ListUtil.difference(followedByList, followsList);
        MySharedPrefs.saveList(UNFOLLOWED_YOU, differenceFollowedBy);
        Log.e(TAG, String.valueOf(differenceFollowedBy.size()));



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.inject(this, view);
        activity.setTitle(getString(R.string.app_name));
        Picasso.with(activity).load(user.getProfile_picture()).into(profileImage);
        userName.setText(user.getUsername());
        follows.setText("" + followsList.size());
        followedBy.setText("" + followedByList.size());
        youUnfollow.setText(differenceFollows.size() + "");
        unfollowYou.setText(differenceFollowedBy.size() + "");

        MediaResponse mediaResponse = MySharedPrefs.loadObject(MEDIA_RESPONSE, MediaResponse.class);
        adapter = new OtherUserListAdapter(mediaResponse);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(8, 4));
        int recyclerheight = 200 * (adapter.getItemCount() / 3 + 1);
        recyclerView.getLayoutParams().height = recyclerheight;
        recyclerView.setAdapter(adapter);

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

        youUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(GeneralListFragment.newInstance(YOU_UNFOLLOWED));
            }
        });

        unfollowYou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.replaceFragment(GeneralListFragment.newInstance(UNFOLLOWED_YOU));
            }
        });

        scrollView.smoothScrollTo(0,0);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class ItemDecorationAlbumColumns extends RecyclerView.ItemDecoration {

        private int mSizeGridSpacingPx;
        private int mGridSize;

        private boolean mNeedLeftSpacing = false;

        public ItemDecorationAlbumColumns(int gridSpacingPx, int gridSize) {
            mSizeGridSpacingPx = gridSpacingPx;
            mGridSize = gridSize;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int frameWidth = (int) ((parent.getWidth() - (float) mSizeGridSpacingPx * (mGridSize - 1)) / mGridSize);
            int padding = parent.getWidth() / mGridSize - frameWidth;
            int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
            if (itemPosition < mGridSize) {
                outRect.top = 0;
            } else {
                outRect.top = mSizeGridSpacingPx;
            }
            if (itemPosition % mGridSize == 0) {
                outRect.left = 0;
                outRect.right = padding;
                mNeedLeftSpacing = true;
            } else if ((itemPosition + 1) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.right = 0;
                outRect.left = padding;
            } else if (mNeedLeftSpacing) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx - padding;
                if ((itemPosition + 2) % mGridSize == 0) {
                    outRect.right = mSizeGridSpacingPx - padding;
                } else {
                    outRect.right = mSizeGridSpacingPx / 2;
                }
            } else if ((itemPosition + 2) % mGridSize == 0) {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx - padding;
            } else {
                mNeedLeftSpacing = false;
                outRect.left = mSizeGridSpacingPx / 2;
                outRect.right = mSizeGridSpacingPx / 2;
            }
            outRect.bottom = 0;
        }
    }
}
