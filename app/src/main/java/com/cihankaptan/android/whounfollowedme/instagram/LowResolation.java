package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class LowResolation implements Parcelable {

    /**
     * height : 320
     * width : 320
     * url : https://scontent.cdninstagram.com/hphotos-xfa1/t51.2885-15/s320x320/e15/11849339_115122218835320_334240479_n.jpg
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

    public LowResolation() {
    }

    protected LowResolation(Parcel in) {
        this.height = in.readInt();
        this.width = in.readInt();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<LowResolation> CREATOR = new Parcelable.Creator<LowResolation>() {
        public LowResolation createFromParcel(Parcel source) {
            return new LowResolation(source);
        }

        public LowResolation[] newArray(int size) {
            return new LowResolation[size];
        }
    };
}
