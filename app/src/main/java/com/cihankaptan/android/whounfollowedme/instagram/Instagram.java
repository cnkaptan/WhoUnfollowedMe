package com.cihankaptan.android.whounfollowedme.instagram;

/**
 * Created by cihan on 10.07.2015.
 */

public class Instagram {

    public static final String AUTHURL = "https://api.instagram.com/oauth/authorize/";
    public static final String TOKENURL = "https://api.instagram.com/oauth/access_token";
    public static final String APIURL = "https://api.instagram.com/v1";
    public static String CALLBACKURL = "http://www.whounfollowedme.com";

    public static final String client_id = "e6b0ffafcad84af4979ddeb233acffe2";
    public static final String client_secret = "a2a3f1ac8df2437589e3ab41ba18462a";




    public static String getAuthURLString(){
        return AUTHURL + "?client_id=" + client_id + "&redirect_uri=" + CALLBACKURL +
                "&response_type=code&display=touch&scope=likes+comments+relationships";
    }

    public static String getTokenURLString(){
        return TOKENURL + "?client_id=" + client_id + "&client_secret=" + client_secret +
                "&redirect_uri=" + CALLBACKURL + "&grant_type=authorization_code";
    }

    public static String getAuthurl(){
        return AUTHURL+"?client_id="+client_id+"&redirect_uri="+CALLBACKURL+"&response_type=token"
                +"&display=touch&scope=likes+comments+relationships";
    }

}
