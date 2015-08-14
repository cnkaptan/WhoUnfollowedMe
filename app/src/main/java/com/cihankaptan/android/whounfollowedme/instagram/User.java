package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cihan on 10.07.2015.
 */
public class User implements Parcelable {

    /**
     * full_name : Snoop Dogg
     * profile_picture : ...
     * id : 1574083
     * username : snoopdogg
     */
    private String full_name;
    private String profile_picture;
    private String id;
    private String username;
    private String bio;
    private String website;
    private CountsEntity counts;

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public CountsEntity getCounts() {
        return counts;
    }

    public void setCounts(CountsEntity counts) {
        this.counts = counts;
    }


    @Override
    public String toString() {
        return String.format("UserName = %s \t FullName = %s \t id = %s",username,full_name,id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.full_name);
        dest.writeString(this.profile_picture);
        dest.writeString(this.id);
        dest.writeString(this.username);
        dest.writeString(this.bio);
        dest.writeString(this.website);
        dest.writeParcelable(this.counts, 0);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.full_name = in.readString();
        this.profile_picture = in.readString();
        this.id = in.readString();
        this.username = in.readString();
        this.bio = in.readString();
        this.website = in.readString();
        this.counts = in.readParcelable(CountsEntity.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
