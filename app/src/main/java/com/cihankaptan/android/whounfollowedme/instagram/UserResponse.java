package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cihan on 10.07.2015.
 */
public class UserResponse implements Parcelable {

    User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.data, 0);
    }

    public UserResponse() {
    }

    protected UserResponse(Parcel in) {
        this.data = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<UserResponse> CREATOR = new Parcelable.Creator<UserResponse>() {
        public UserResponse createFromParcel(Parcel source) {
            return new UserResponse(source);
        }

        public UserResponse[] newArray(int size) {
            return new UserResponse[size];
        }
    };
}
