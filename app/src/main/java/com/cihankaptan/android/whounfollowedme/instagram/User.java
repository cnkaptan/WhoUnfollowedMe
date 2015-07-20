package com.cihankaptan.android.whounfollowedme.instagram;

/**
 * Created by cihan on 10.07.2015.
 */
public class User{

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
}
