package com.example.safwat.twitterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class Login extends AppCompatActivity implements LoginViewInterface {

    TwitterLoginButton loginButton;
    LoginPresenterInterface loginPresenterInterface;
    private static final String LOG_CAT = Login.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenterInterface = new LoginPresenter(this);
        loginButton =(TwitterLoginButton) findViewById(R.id.Login_Button);
        //check if user login before if true swap to follower activity else show login activity.
        loginPresenterInterface.checkUserLogin();
    }

    // we need to override onActivityResult to get data from opening browser ,
    // that regarded that the user allow to access her twitter account and we get the Auth success
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginButton.onActivityResult(requestCode,resultCode,data);
    }

    // this called after user clicked on twitter login button and he auth with his account
    // and swap to next screen or activity.
    @Override
    public void onSuccessLogin() {
        loginPresenterInterface.NextActivity();
    }

    // this called after user clicked on twitter login button and he auth with his account
    // but there is  an error occur.
    @Override
    public void onFailureLogin(TwitterException exception) {
        Log.e(LOG_CAT,"there is exception in request :\n"+exception.getMessage());
    }

    // in this case user open the app for first time or he sign out so login screen will be show if
    // he close the app  and open it again.
    @Override
    public void UserFirstTimeLogin() {
        loginButton.setEnabled(true);
        loginButton.setCallback((LoginPresenter)loginPresenterInterface);
    }

}
