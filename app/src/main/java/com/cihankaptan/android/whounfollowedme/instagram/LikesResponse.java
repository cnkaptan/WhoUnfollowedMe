package com.cihankaptan.android.whounfollowedme.instagram;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 18/08/15.
 */
public class LikesResponse {
    ArrayList<User> data;

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
