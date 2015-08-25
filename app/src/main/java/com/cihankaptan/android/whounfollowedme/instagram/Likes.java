package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class Likes implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.count);
        dest.writeTypedList(data);
    }

    public Likes() {
    }

    protected Likes(Parcel in) {
        this.count = in.readInt();
        this.data = in.createTypedArrayList(User.CREATOR);
    }

    public static final Parcelable.Creator<Likes> CREATOR = new Parcelable.Creator<Likes>() {
        public Likes createFromParcel(Parcel source) {
            return new Likes(source);
        }

        public Likes[] newArray(int size) {
            return new Likes[size];
        }
    };
}
