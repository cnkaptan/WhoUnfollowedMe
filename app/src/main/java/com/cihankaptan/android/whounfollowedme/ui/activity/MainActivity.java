package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.FollowsResponse;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;
import com.cihankaptan.android.whounfollowedme.ui.fragment.InstagramFragment;
import com.cihankaptan.android.whounfollowedme.ui.fragment.ProfileFragment;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements Constans{
    private static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager manager;
    private FragmentTransaction transaction;
    public InstagramApi instagramApi;
    private ProgressDialog progressDialog;
    private String access_token;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRetrofitAdapter();

        manager = getFragmentManager();
        access_token = MySharedPrefs.loadString(ACCESS_TOKEN);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        progressDialog = new ProgressDialog(this);





    }

    @Override
    protected void onStart() {
        super.onStart();
        if (access_token != null){
            Log.e(TAG,access_token);
            progressDialog = ProgressDialog.show(this,"Yukleniyor","Bilgileriniz Yukleniyor");
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
                            String cursor = null;
                            do{
                                followsResponse = instagramApi.getFollowedBy(access_token,cursor);
                                cursor = followsResponse.getPagination().getNext_cursor();
                                followedByUsers.addAll(followsResponse.getData());
                            }while (followsResponse.getPagination().getNext_cursor() != null);

                            do{
                                followsResponse = instagramApi.getFollows(access_token, cursor);
                                cursor = followsResponse.getPagination().getNext_cursor();
                                followsUsers.addAll(followsResponse.getData());
                            }while (followsResponse.getPagination().getNext_cursor() != null);


                            MySharedPrefs.saveObject(USER, userResponse.getData());
                            MySharedPrefs.saveList(FOLLOWEDBY_LIST, followedByUsers);
                            MySharedPrefs.saveList(FOLLOWS_LIST, followsUsers);
                            progressDialog.dismiss();
                            Log.e(TAG, FOLLOWEDBY_LIST + " = " + MySharedPrefs.loadList(FOLLOWEDBY_LIST, User.class).size());
                            Log.e(TAG, FOLLOWS_LIST + " = " + MySharedPrefs.loadList(FOLLOWS_LIST, User.class).size());


                            replaceFragment(ProfileFragment.newInstance());
                        }
                    }).start();
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });
        }else{
            Log.e(TAG,"Access token 0");
            replaceFragment(InstagramFragment.newInstance());
        }
    }

    @SuppressLint("CommitTransaction")
    public void addFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("CommitTransaction")
    public void replaceFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void setRetrofitAdapter(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
    }

    @Override
    public void onBackPressed() {

        if (manager.getBackStackEntryCount() > 1){
            Log.e(TAG,manager.getBackStackEntryCount()+"");
            manager.popBackStack();
        }else{
            super.onBackPressed();

        }
    }

    public void setTitle(String str){
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#2A2D34\">" + str + "</font>")));
    }

}
