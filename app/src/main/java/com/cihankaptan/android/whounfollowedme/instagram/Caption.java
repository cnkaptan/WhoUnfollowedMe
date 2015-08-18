package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 18/08/15.
 */
public class Caption implements Parcelable {

    private User from;
    private String id;
    private String text;
    private String created_time;

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.from, 0);
        dest.writeString(this.id);
        dest.writeString(this.text);
        dest.writeString(this.created_time);
    }

    public Caption() {
    }

    protected Caption(Parcel in) {
        this.from = in.readParcelable(User.class.getClassLoader());
        this.id = in.readString();
        this.text = in.readString();
        this.created_time = in.readString();
    }

    public static final Parcelable.Creator<Caption> CREATOR = new Parcelable.Creator<Caption>() {
        public Caption createFromParcel(Parcel source) {
            return new Caption(source);
        }

        public Caption[] newArray(int size) {
            return new Caption[size];
        }
    };
}
