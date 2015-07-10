package com.cihankaptan.android.whounfollowedme.instagram;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by cihan on 10.07.2015.
 */
public class FollowsResponse implements Parcelable {


    private List<User> data;
    private Pagination pagination;

    public void setData(List<User> data) {
        this.data = data;
    }

    public List<User> getData() {
        return data;
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
        dest.writeTypedList(data);
        dest.writeParcelable(this.pagination, 0);
    }

    public FollowsResponse() {
    }

    protected FollowsResponse(Parcel in) {
        this.data = in.createTypedArrayList(User.CREATOR);
        this.pagination = in.readParcelable(Pagination.class.getClassLoader());
    }

    public static final Parcelable.Creator<FollowsResponse> CREATOR = new Parcelable.Creator<FollowsResponse>() {
        public FollowsResponse createFromParcel(Parcel source) {
            return new FollowsResponse(source);
        }

        public FollowsResponse[] newArray(int size) {
            return new FollowsResponse[size];
        }
    };
}
