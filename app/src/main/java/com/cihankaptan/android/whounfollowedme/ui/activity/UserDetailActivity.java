package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.ui.fragment.OtherUserProfileFragment;

public class UserDetailActivity extends BaseActivity {

    private static final String TAG = UserDetailActivity.class.getSimpleName();
    private User user;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        fragmentManager = getFragmentManager();
        user = getIntent().getParcelableExtra("user");
        Log.e(TAG,user.getFull_name());

        addFragment(OtherUserProfileFragment.newInstance(user));
    }


    @SuppressLint("CommitTransaction")
    public void addFragment(Fragment fragment) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }
}
