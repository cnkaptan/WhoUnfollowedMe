package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.eventbus.EventBusAdapter;
import com.cihankaptan.android.whounfollowedme.eventbus.MedieEvent;
import com.cihankaptan.android.whounfollowedme.instagram.FollowsResponse;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;
import com.cihankaptan.android.whounfollowedme.ui.fragment.ImageShowFragment;
import com.cihankaptan.android.whounfollowedme.ui.fragment.InstagramFragment;
import com.cihankaptan.android.whounfollowedme.ui.fragment.ProfileFragment;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.ListUtil;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ProfileActivity extends BaseActivity implements Constans{

    private static final String TAG = ProfileActivity.class.getSimpleName();
    @InjectView(R.id.title)
    FontTextView title;
    @InjectView(R.id.search)
    ImageView search;
    @InjectView(R.id.refresh)
    ImageView refresh;
    @InjectView(R.id.frame)
    FrameLayout frame;
    private Toolbar toolbar;
    private FragmentManager manager;
    private String access_token;
    private FragmentTransaction transaction;
    private ArrayList<User> allUsers;
    private ProgressDialog progressDialog;
    private InstagramApi instagramApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.inject(this);

        setRetrofitAdapter();
        manager = getFragmentManager();
//        MySharedPrefs.deleteShared(ACCESS_TOKEN);
        access_token = MySharedPrefs.loadString(ACCESS_TOKEN);
        allUsers = getIntent().getParcelableArrayListExtra(ALL_USERS);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        EventBusAdapter.register(this);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, SearchActivity.class);
                intent.putParcelableArrayListExtra(ALL_USERS,allUsers);
                startActivity(intent);
            }
        });

        replaceFragment(ProfileFragment.newInstance());

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (access_token != null) {
                    Log.e(TAG, access_token);
                    progressDialog = ProgressDialog.show(ProfileActivity.this, getResources().getString(R.string.loading),getResources().getString(R.string.information_loading));
                    progressDialog.setCancelable(true);
                    instagramApi.getUserInfo(access_token, new Callback<UserResponse>() {
                        private List<User> followedByUsers = new ArrayList<>();
                        private List<User> followsUsers = new ArrayList<>();

                        @Override
                        public void success(final UserResponse userResponse, Response response) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    FollowsResponse followsResponse;
                                    MediaResponse mediaResponse = instagramApi.getRecentPics(access_token);
                                    MySharedPrefs.saveObject(MEDIA_RESPONSE, mediaResponse);
                                    String cursor = null;
                                    do {
                                        followsResponse = instagramApi.getFollowedBy(access_token, cursor);
                                        cursor = followsResponse.getPagination().getNext_cursor();
                                        followedByUsers.addAll(followsResponse.getData());
                                    } while (followsResponse.getPagination().getNext_cursor() != null);

                                    do {
                                        followsResponse = instagramApi.getFollows(access_token, cursor);
                                        cursor = followsResponse.getPagination().getNext_cursor();
                                        followsUsers.addAll(followsResponse.getData());
                                    } while (followsResponse.getPagination().getNext_cursor() != null);


                                    allUsers = (ArrayList<User>) ListUtil.union(followsUsers, followedByUsers);

                                    MySharedPrefs.saveObject(USER, userResponse.getData());
                                    MySharedPrefs.saveList(FOLLOWEDBY_LIST, followedByUsers);
                                    MySharedPrefs.saveList(FOLLOWS_LIST, followsUsers);
                                    MySharedPrefs.saveList(ALL_USERS, allUsers);

                                    progressDialog.dismiss();
                                    Log.e(TAG, FOLLOWEDBY_LIST + " = " + MySharedPrefs.loadList(FOLLOWEDBY_LIST, User.class).size());
                                    Log.e(TAG, FOLLOWS_LIST + " = " + MySharedPrefs.loadList(FOLLOWS_LIST, User.class).size());

                                    manager.popBackStack();
                                    replaceFragment(ProfileFragment.newInstance());
                                }
                            }).start();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            progressDialog.cancel();
                            if (!isNetworkAvailable()) {

                                showMaterialDialogNetwork();
                            }
                        }
                    });
                } else {
                    replaceFragment(InstagramFragment.newInstance());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusAdapter.unregister(this);
    }

    @SuppressLint("CommitTransaction")
    public void addFragment(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("CommitTransaction")
    public void replaceFragment(Fragment fragment) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void setTitle(String str) {
        title.setText((Html.fromHtml("<font color=\"#2A2D34\">" + str + "</font>")));
    }

    @Override
    public void onBackPressed() {

        if (manager.getBackStackEntryCount() > 1) {
            Log.e(TAG, manager.getBackStackEntryCount() + "");
            manager.popBackStack();
        } else {
            super.onBackPressed();

        }
    }

    public void setRetrofitAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
    }

    @Subscribe
    public void getMediaEvent(MedieEvent medieEvent){
        addFragment(ImageShowFragment.newInstance(medieEvent.getMedia()));
    }


}
