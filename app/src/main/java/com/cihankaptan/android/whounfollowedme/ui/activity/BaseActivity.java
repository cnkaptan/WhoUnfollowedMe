package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cihankaptan.android.whounfollowedme.InstagramApi;

/**
 * Created by cnkaptan on 23/08/15.
 */
public class BaseActivity extends AppCompatActivity {

    public InstagramApi instagramApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!isNetworkAvailable()) {

            showMaterialDialogNetwork();
        }
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }


        return isAvailable;
    }

    public void showMaterialDialogNetwork() {


        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .title("Internet")
                .content("Uygulama internet gerektirmektedir.Lütfen internete baglanınız.")
                .positiveText("Internet")
                .negativeText("Mobile")
                .neutralText("Çıkış");
        builder.callback(new MaterialDialog.ButtonCallback() {
            @Override
            public void onPositive(MaterialDialog dialog) {
                super.onPositive(dialog);
                startActivityForResult(new Intent(Settings.ACTION_SETTINGS), 0);
            }

            @Override
            public void onNegative(MaterialDialog dialog) {
                super.onNegative(dialog);
//                               startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS),0);
                startActivityForResult(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS), 0);
            }


            @Override
            public void onNeutral(MaterialDialog dialog) {
                super.onNeutral(dialog);
                finish();

            }
        });
        builder.show();


    }
}
