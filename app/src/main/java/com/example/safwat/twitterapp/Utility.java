package com.example.safwat.twitterapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;

import com.example.safwat.twitterapp.Activity.Followers;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;

import java.util.Locale;
import java.util.prefs.Preferences;

/**
 * Created by Safwat on 4/3/2018.
 */

public class Utility {

    // check connection validation
    public static boolean IsNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
