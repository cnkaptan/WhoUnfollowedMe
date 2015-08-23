package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.adapter.OtherUserListAdapter;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OtherUserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OtherUserProfileFragment extends Fragment implements Constans {
    private static final String TAG = OtherUserProfileFragment.class.getSimpleName();
    Activity activity;
    User user;
    @InjectView(R.id.profilePhoto)
    ImageView profilePhoto;
    @InjectView(R.id.full_name)
    FontTextView fullName;
    @InjectView(R.id.user_name)
    FontTextView userName;
    @InjectView(R.id.reyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.detailButton)
    Button detailButton;
    @InjectView(R.id.follows)
    FontTextView follows;
    @InjectView(R.id.followedBy)
    FontTextView followedBy;

    private OtherUserListAdapter adapter;
    private String accessToken;
    private Dialog progressDialog;
    private InstagramApi instagramApi;

    public OtherUserProfileFragment() {
        // Required empty public constructor
    }

    public static OtherUserProfileFragment newInstance(User user) {
        OtherUserProfileFragment fragment = new OtherUserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable("user");
        }
        setRetrofitAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_other_user_profile, container, false);
        ButterKnife.inject(this, view);

        accessToken = MySharedPrefs.loadString(ACCESS_TOKEN);
        progressDialog = ProgressDialog.show(activity,getResources().getString(R.string.loading), getResources().getString(R.string.information_loading));
        progressDialog.setCancelable(true);
        instagramApi.getRecentPics(user.getId(), accessToken, new Callback<MediaResponse>() {
            @Override
            public void success(MediaResponse mediaResponse, Response response) {

                instagramApi.getUserInfo(user.getId(), accessToken, new Callback<UserResponse>() {
                    @Override
                    public void success(final UserResponse userResponse, Response response) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.cancel();
                                follows.setText(userResponse.getData().getCounts().getFollows()+"");
                                followedBy.setText(userResponse.getData().getCounts().getFollowed_by()+"");
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                adapter = new OtherUserListAdapter(mediaResponse);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(activity, 4);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.addItemDecoration(new ItemDecorationAlbumColumns(8, 4));
                int recyclerheight = 200 * (adapter.getItemCount() / 3 + 1);
                recyclerView.getLayoutParams().height = recyclerheight;
                recyclerView.setAdapter(adapter);
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
        detailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/" + user.getUsername());
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    activity.startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    activity.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/" + user.getUsername())));
                }

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void setRetrofitAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
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
