package com.example.safwat.twitterapp.Application;

import android.app.Application;
import android.util.Log;

import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

/**
 * Created by Safwat on 4/2/2018.
 */

public class TwitterApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // this line initialize twitter component that allow you to using Twitter button
        // without this line twitter button will be not active and you can not get Auth without initialize the twitter
        // to connected with consumer_key and consumer_secret to get config
        Twitter.initialize(this);
    }
}
