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
import android.widget.FrameLayout;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.FollowsResponse;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;
import com.cihankaptan.android.whounfollowedme.ui.fragment.InstagramFragment;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.ListUtil;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends BaseActivity implements Constans {
    private static final String TAG = MainActivity.class.getSimpleName();
    public InstagramApi instagramApi;
    @InjectView(R.id.title)
    FontTextView title;
    @InjectView(R.id.frame)
    FrameLayout frame;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private ProgressDialog progressDialog;
    private String access_token;
    private Toolbar toolbar;
    private ArrayList<User> allUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setRetrofitAdapter();
        manager = getFragmentManager();
//        MySharedPrefs.deleteShared(ACCESS_TOKEN);
        access_token = MySharedPrefs.loadString(ACCESS_TOKEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setTitle("WHO UNFOLLOWED ME");
        progressDialog = new ProgressDialog(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (access_token != null) {
            Log.e(TAG, access_token);
            progressDialog = ProgressDialog.show(this, getResources().getString(R.string.loading),getResources().getString(R.string.information_loading));
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


                            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                            finish();
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

    public void setRetrofitAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
    }



    public void setTitle(String str) {
        title.setText((Html.fromHtml("<font color=\"#2A2D34\">" + str + "</font>")));
    }


}
