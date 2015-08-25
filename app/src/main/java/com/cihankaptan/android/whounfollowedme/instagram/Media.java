package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by cnkaptan on 14/08/15.
 */
public class Media implements Parcelable {

    Likes likes;
    Images images;
    Caption caption;
    String id;
    Pagination pagination;

    public Likes getLikes() {
        return likes;
    }

    public void setLikes(Likes likes) {
        this.likes = likes;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public Caption getCaption() {
        return caption;
    }

    public void setCaption(Caption caption) {
        this.caption = caption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.likes, flags);
        dest.writeParcelable(this.images, 0);
        dest.writeParcelable(this.caption, 0);
        dest.writeString(this.id);
        dest.writeParcelable(this.pagination, 0);
    }

    public Media() {
    }

    protected Media(Parcel in) {
        this.likes = in.readParcelable(Likes.class.getClassLoader());
        this.images = in.readParcelable(Images.class.getClassLoader());
        this.caption = in.readParcelable(Caption.class.getClassLoader());
        this.id = in.readString();
        this.pagination = in.readParcelable(Pagination.class.getClassLoader());
    }

    public static final Parcelable.Creator<Media> CREATOR = new Parcelable.Creator<Media>() {
        public Media createFromParcel(Parcel source) {
            return new Media(source);
        }

        public Media[] newArray(int size) {
            return new Media[size];
        }
    };
}
