package com.cihankaptan.android.whounfollowedme.instagram;

import java.util.ArrayList;

/**
 * Created by cnkaptan on 13/08/15.
 */
public class MediaResponse {

  ArrayList<Media> data;

    public ArrayList<Media> getMedias() {
        return data;
    }

    public void setMedias(ArrayList<Media> data) {
        this.data = data;
    }
}
