package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cihan on 10.07.2015.
 */
public class Pagination implements Parcelable {

    /**
     * next_cursor : 1430130947968
     * next_url : https://api.instagram.com/v1/users/278777987/follows?access_token=278777987.e6b0ffa.aa191a7d2af346b79eb24de467e35f1f&cursor=1430130947968
     */
    private String next_cursor;
    private String next_url;

    public void setNext_cursor(String next_cursor) {
        this.next_cursor = next_cursor;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }

    public String getNext_cursor() {
        return next_cursor;
    }

    public String getNext_url() {
        return next_url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.next_cursor);
        dest.writeString(this.next_url);
    }

    public Pagination() {
    }

    protected Pagination(Parcel in) {
        this.next_cursor = in.readString();
        this.next_url = in.readString();
    }

    public static final Parcelable.Creator<Pagination> CREATOR = new Parcelable.Creator<Pagination>() {
        public Pagination createFromParcel(Parcel source) {
            return new Pagination(source);
        }

        public Pagination[] newArray(int size) {
            return new Pagination[size];
        }
    };
}
