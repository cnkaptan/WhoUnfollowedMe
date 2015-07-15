package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.ui.fragment.InstagramFragment;


public class MainActivity extends ActionBarActivity {
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getFragmentManager();

        addFragment(InstagramFragment.newInstance());

    }

    @SuppressLint("CommitTransaction")
    public void addFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.add(R.id.frame, fragment)
                .addToBackStack(null)
                .commit();
    }

    @SuppressLint("CommitTransaction")
    public void replaceFragment(Fragment fragment){
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frame,fragment)
                .addToBackStack(null)
                .commit();
    }
}
