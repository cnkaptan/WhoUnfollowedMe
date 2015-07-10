package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cihan on 10.07.2015.
 */
public class AccessTokenResponse implements Parcelable {


    /**
     * access_token : fb2e77d.47a0479900504cb3ab4a1f626d174d2d
     * user : {"full_name":"Snoop Dogg","profile_picture":"...","id":"1574083","username":"snoopdogg"}
     */
    private String access_token;
    private User user;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAccess_token() {
        return access_token;
    }

    public User getUser() {
        return user;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.access_token);
        dest.writeParcelable(this.user, 0);
    }

    public AccessTokenResponse() {
    }

    protected AccessTokenResponse(Parcel in) {
        this.access_token = in.readString();
        this.user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Parcelable.Creator<AccessTokenResponse> CREATOR = new Parcelable.Creator<AccessTokenResponse>() {
        public AccessTokenResponse createFromParcel(Parcel source) {
            return new AccessTokenResponse(source);
        }

        public AccessTokenResponse[] newArray(int size) {
            return new AccessTokenResponse[size];
        }
    };
}
