package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cihankaptan.android.whounfollowedme.InstagramApi;
import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.FollowsResponse;
import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.instagram.UserResponse;
import com.cihankaptan.android.whounfollowedme.util.Constans;
import com.cihankaptan.android.whounfollowedme.util.InstagramApp;
import com.cihankaptan.android.whounfollowedme.util.MySharedPrefs;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstagramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstagramFragment extends Fragment implements Constans{


    private View view;
    private final String TAG = InstagramFragment.class.getSimpleName();
    private ProgressDialog progressDialog;
    private InstagramApi instagramApi;
    private ArrayList<User> followedByUsers,followsUsers;
    private Activity activity;
    private InstagramApp app;

    public static InstagramFragment newInstance() {
        InstagramFragment fragment = new InstagramFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public InstagramFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
        app = (InstagramApp) activity.getApplication();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setRetrofitAdapter();
        followedByUsers = new ArrayList<User>();
        followsUsers = new ArrayList<User>();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_instagram, container, false);
        CookieSyncManager.createInstance(activity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();


        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Instagram.getAuthurl());
        WebSettings ws = webView.getSettings();
        ws.setSaveFormData(false);
        ws.setSavePassword(false);
        progressDialog = new ProgressDialog(container.getContext());

        return view;
    }




    private class AuthWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Instagram.CALLBACKURL)) {
                Log.e(TAG,url);
                String parts[] = url.split("=");
                final String access_token = parts[1];  //This is your request token.
                progressDialog = ProgressDialog.show(activity,"Yukleniyor","Bilgileriniz Yukleniyor");
                progressDialog.setCancelable(true);

                instagramApi.getUserInfo(access_token, new Callback<UserResponse>() {

                    @Override
                    public void success(final UserResponse userResponse, Response response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FollowsResponse followsResponse;
                        String cursor = null;
                        do{
                            followsResponse = instagramApi.getFollowedBy(access_token,cursor);
                            cursor = followsResponse.getPagination().getNext_cursor();
                            followedByUsers.addAll(followsResponse.getData());
                        }while (followsResponse.getPagination().getNext_cursor() != null);

                        do{
                            followsResponse = instagramApi.getFollows(access_token, cursor);
                            cursor = followsResponse.getPagination().getNext_cursor();
                            followsUsers.addAll(followsResponse.getData());
                        }while (followsResponse.getPagination().getNext_cursor() != null);


                        MySharedPrefs.saveObject(USER, userResponse.getData());
                        MySharedPrefs.saveList(FOLLOWEDBY_LIST, followedByUsers);
                        MySharedPrefs.saveList(FOLLOWS_LIST, followsUsers);
                        progressDialog.dismiss();
                        Log.e(TAG, FOLLOWEDBY_LIST + " = " + MySharedPrefs.loadList(FOLLOWEDBY_LIST, User.class).size());
                        Log.e(TAG,FOLLOWS_LIST+" = "+MySharedPrefs.loadList(FOLLOWS_LIST,User.class).size());

                        FragmentManager fragmentManager = getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment.newInstance()).commit();
                    }
                }).start();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });

                return true;
            }
            return false;
        }
    }


    public void setRetrofitAdapter(){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Instagram.APIURL)
                .build();

        instagramApi = restAdapter.create(InstagramApi.class);
    }
}
