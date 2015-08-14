package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class Thumbnail implements Parcelable {

    /**
     * height : 150
     * width : 150
     * url : https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s150x150/e15/11849339_115122218835320_334240479_n.jpg
     */
    private int height;
    private int width;
    private String url;

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.height);
        dest.writeInt(this.width);
        dest.writeString(this.url);
    }

    public Thumbnail() {
    }

    protected Thumbnail(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<Thumbnail> CREATOR = new Parcelable.Creator<Thumbnail>() {
        public Thumbnail createFromParcel(Parcel source) {
            return new Thumbnail(source);
        }

        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };
}
