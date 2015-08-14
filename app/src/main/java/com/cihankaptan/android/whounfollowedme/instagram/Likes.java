package com.cihankaptan.android.whounfollowedme.instagram;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class Likes {
    int count;
    ArrayList<User> data;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<User> getData() {
        return data;
    }

    public void setData(ArrayList<User> data) {
        this.data = data;
    }
}
