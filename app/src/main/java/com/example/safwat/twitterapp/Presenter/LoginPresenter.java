package com.example.safwat.twitterapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.safwat.twitterapp.Activity.Followers;
import com.example.safwat.twitterapp.Activity.Login;
import com.example.safwat.twitterapp.PresenterInterface.LoginPresenterInterface;
import com.example.safwat.twitterapp.View.LoginViewInterface;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import com.twitter.sdk.android.core.Callback;

/**
 * Created by Safwat on 4/2/2018.
 */

public class LoginPresenter extends Callback<TwitterSession> implements LoginPresenterInterface {

     private Activity activity;
    private LoginViewInterface loginview;

    public LoginPresenter(Login view){
         loginview = view;
        this.activity = view;
    }

    @Override
    public void success(Result<TwitterSession> result) {
        loginview.onSuccessLogin();
    }

    @Override
    public void failure(TwitterException exception) {
        loginview.onFailureLogin(exception);
    }

    // check user OAuth  if he login before then swap him to follower activity
    // else then show the twitter button to Authentication.
    @Override
    public void checkUserLogin() {
        if(TwitterCore.getInstance().getSessionManager().getActiveSession() != null)
                loginview.onSuccessLogin();
        else
            loginview.UserFirstTimeLogin();
    }

    // swap to follower activity and destroy this activity.
    @Override
    public void NextActivity() {
        Intent i = new Intent(activity,Followers.class);
        activity.startActivity(i);
        activity.finish();
    }
}
