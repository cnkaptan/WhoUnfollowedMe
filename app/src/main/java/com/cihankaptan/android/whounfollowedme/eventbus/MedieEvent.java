package com.cihankaptan.android.whounfollowedme.eventbus;

import android.os.Parcel;
import android.os.Parcelable;

import com.cihankaptan.android.whounfollowedme.instagram.Media;

/**
 * Created by cnkaptan on 25/08/15.
 */
public class MedieEvent implements Parcelable {

    Media media;

    public MedieEvent(Media media) {
        this.media = media;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.media, flags);
    }

    protected MedieEvent(Parcel in) {
        this.media = in.readParcelable(Media.class.getClassLoader());
    }

    public static final Parcelable.Creator<MedieEvent> CREATOR = new Parcelable.Creator<MedieEvent>() {
        public MedieEvent createFromParcel(Parcel source) {
            return new MedieEvent(source);
        }

        public MedieEvent[] newArray(int size) {
            return new MedieEvent[size];
        }
    };
}
