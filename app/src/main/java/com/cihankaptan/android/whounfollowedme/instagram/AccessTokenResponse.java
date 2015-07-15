package com.cihankaptan.android.whounfollowedme.instagram;

/**
 * Created by cihan on 10.07.2015.
 */
public class AccessTokenResponse{


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



}
