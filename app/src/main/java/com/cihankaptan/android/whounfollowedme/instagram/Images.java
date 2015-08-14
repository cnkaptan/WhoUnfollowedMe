package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class Images implements Parcelable {

    LowResolation low_resolution;
    Thumbnail thumbnail;
    StandartRosulation standard_resolution;

    public LowResolation getLow_resolution() {
        return low_resolution;
    }

    public void setLow_resolution(LowResolation low_resolution) {
        this.low_resolution = low_resolution;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public StandartRosulation getStandart_resulation() {
        return standard_resolution;
    }

    public void setStandart_resulation(StandartRosulation standard_resolution) {
        this.standard_resolution = standard_resolution;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.low_resolution, 0);
        dest.writeParcelable(this.thumbnail, 0);
        dest.writeParcelable(this.standard_resolution, 0);
    }

    public Images() {
    }

    protected Images(Parcel in) {
        this.low_resolution = in.readParcelable(LowResolation.class.getClassLoader());
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.standard_resolution = in.readParcelable(StandartRosulation.class.getClassLoader());
    }

    public static final Parcelable.Creator<Images> CREATOR = new Parcelable.Creator<Images>() {
        public Images createFromParcel(Parcel source) {
            return new Images(source);
        }

        public Images[] newArray(int size) {
            return new Images[size];
        }
    };
}
