package com.cihankaptan.android.whounfollowedme.util;

import android.app.Application;

import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;

/**
 * Created by cihan on 13.07.2015.
 */
public class InstagramApp extends Application {
    public static MySharedPrefs sharedPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPrefs = new MySharedPrefs(this);
    }
}
