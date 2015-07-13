package com.cihankaptan.android.whounfollowedme;

import android.app.Application;

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
