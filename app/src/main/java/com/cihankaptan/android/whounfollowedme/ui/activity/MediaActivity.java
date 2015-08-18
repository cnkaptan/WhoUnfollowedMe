package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.LikesResponse;
import com.cihankaptan.android.whounfollowedme.instagram.Media;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit.RestAdapter;

public class MediaActivity extends AppCompatActivity implements Constans {

    private static final String TAG = MediaActivity.class.getSimpleName();
    private InstagramApi instagramApi;
    private ArrayList<User> users;
    private HashMap<String, ArrayList<Media>> mediaMap;
    private Handler mHandler;
    private String access_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media);
        setRetrofitAdapter();

        users = new ArrayList<User>();
        mediaMap = new HashMap<String, ArrayList<Media>>();
        mHandler = new Handler();

        final MediaResponse mediaResponse = MySharedPrefs.loadObject(MEDIA_RESPONSE, MediaResponse.class);
        access_token = MySharedPrefs.loadString(ACCESS_TOKEN);
        final ArrayList<LikesResponse> likesResponses = new ArrayList<LikesResponse>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Media media : mediaResponse.getMedias()) {
                    LikesResponse likesResponse = instagramApi.getLikes(media.getId(), access_token);
                    likesResponses.add(likesResponse);
                }
                Log.e(TAG,likesResponses.size()+"");
            }
        }).start();
    }


    public void setRetrofitAdapter() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
    }
}
