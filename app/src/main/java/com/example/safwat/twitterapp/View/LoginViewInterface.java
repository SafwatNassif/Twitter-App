package com.example.safwat.twitterapp.View;

import com.twitter.sdk.android.core.TwitterException;

/**
 * Created by Safwat on 4/2/2018.
 */

public interface LoginViewInterface {
        void onSuccessLogin();
        void onFailureLogin(TwitterException exception);
        void UserFirstTimeLogin();
}
