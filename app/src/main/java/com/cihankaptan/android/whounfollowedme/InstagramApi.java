package com.cihankaptan.android.whounfollowedme;

import android.support.annotation.Nullable;

import com.cihankaptan.android.whounfollowedme.instagram.FollowsResponse;
import com.cihankaptan.android.whounfollowedme.instagram.MediaResponse;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by cihan on 10.07.2015.
 */
public interface InstagramApi {

    @GET("/users/self")
    void getUserInfo(@Query("access_token")String accessToken,Callback<UserResponse> userResponseCallback);

    @GET("/users/self/follows")
    void getFollows(@Query("access_token")String accessToken,Callback<FollowsResponse> followsResponseCallback);

    @GET("/users/self/follows")
    FollowsResponse getFollows(@Query("access_token")String accessToken,@Nullable @Query("cursor")String cursor);

    @GET("/users/self/followed-by")
    void getFollowedBy(@Query("access_token")String accessToken,Callback<FollowsResponse> followsResponseCallback);

    @GET("/users/self/followed-by")
    FollowsResponse getFollowedBy(@Query("access_token")String accessToken,@Nullable @Query("cursor")String cursor);



    @GET("/users/{user-id}/media/recent/")
    void getRecentPics(@Path("user-id")String userId,@Query("access_token")String accessToken,Callback<MediaResponse> mediaResponseCallback);
}
