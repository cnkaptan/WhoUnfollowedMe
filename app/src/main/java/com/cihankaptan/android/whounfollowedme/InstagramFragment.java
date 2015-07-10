package com.cihankaptan.android.whounfollowedme;


import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cihankaptan.android.whounfollowedme.instagram.Instagram;
import com.cihankaptan.android.whounfollowedme.instagram.User;

import java.util.ArrayList;

import retrofit.RestAdapter;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstagramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstagramFragment extends Fragment {

    private View view;
    private final String TAG = InstagramFragment.class.getSimpleName();
    private InstagramApi instagramApi;
    private ArrayList<User> users;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        setRetrofitAdapter();
        users = new ArrayList<User>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_instagram, container, false);

        WebView webView = (WebView) view.findViewById(R.id.webView);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(Instagram.getAuthurl());
        return view;
    }




    private class AuthWebViewClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.startsWith(Instagram.CALLBACKURL)) {
                Log.e(TAG,url);
                String parts[] = url.split("=");
                final String access_token = parts[1];  //This is your request token.
////                Log.e(TAG,url);
//                Log.e(TAG, access_token);
//
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        FollowsResponse followsResponse;
//                        String cursor = null;
//                        do{
//                            followsResponse = instagramApi.getFollowedBy(access_token,cursor);
//                            cursor = followsResponse.getPagination().getNext_cursor();
//                            users.addAll(followsResponse.getData());
//                        }while (followsResponse.getPagination().getNext_cursor() != null);
//
//                        Log.e(TAG,users.size()+"");
//                    }
//                }).start();
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
