package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cihan on 10.07.2015.
 */
public class Counts implements Parcelable {

    private int followed_by;
    private int follows;
    private int media;

    public void setFollowed_by(int followed_by) {
        this.followed_by = followed_by;
    }

    public void setFollows(int follows) {
        this.follows = follows;
    }

    public void setMedia(int media) {
        this.media = media;
    }

    public int getFollowed_by() {
        return followed_by;
    }

    public int getFollows() {
        return follows;
    }

    public int getMedia() {
        return media;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.followed_by);
        dest.writeInt(this.follows);
        dest.writeInt(this.media);
    }

    public Counts() {
    }

    protected Counts(Parcel in) {
        this.followed_by = in.readInt();
        this.follows = in.readInt();
        this.media = in.readInt();
    }

    public static final Parcelable.Creator<Counts> CREATOR = new Parcelable.Creator<Counts>() {
        public Counts createFromParcel(Parcel source) {
            return new Counts(source);
        }

        public Counts[] newArray(int size) {
            return new Counts[size];
        }
    };
}
