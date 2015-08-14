package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class StandartRosulation implements Parcelable {



    /**
     * height : 640
     * width : 640
     * url : https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s640x640/sh0.08/e35/11849339_115122218835320_334240479_n.jpg
     */
    private int height;
    private int width;
    private String url;

    public StandartRosulation() {
    }

    protected StandartRosulation(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        this.url = in.readString();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public static final Parcelable.Creator<StandartRosulation> CREATOR = new Parcelable.Creator<StandartRosulation>() {
        public StandartRosulation createFromParcel(Parcel source) {
            return new StandartRosulation(source);
        }

        public StandartRosulation[] newArray(int size) {
            return new StandartRosulation[size];
        }
    };
}
