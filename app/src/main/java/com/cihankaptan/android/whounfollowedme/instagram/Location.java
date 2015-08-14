package com.cihankaptan.android.whounfollowedme.instagram;

/**
 * Created by cnkaptan on 13/08/15.
 */
public class Location {
    /**
     * id : 833
     * name : Civic Center BART
     * longitude : -122.4138736724854
     * latitude : 37.77956816727314
     */
    private String id;
    private String name;
    private double longitude;
    private double latitude;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }
}
