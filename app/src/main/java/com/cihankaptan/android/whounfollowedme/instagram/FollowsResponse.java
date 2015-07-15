package com.cihankaptan.android.whounfollowedme.instagram;

import java.util.List;

/**
 * Created by cihan on 10.07.2015.
 */
public class FollowsResponse  {


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


}
