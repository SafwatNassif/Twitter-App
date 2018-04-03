package com.example.safwat.twitterapp.Service;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by Safwat on 4/2/2018.
 */

public class CustomApiClient extends TwitterApiClient {

    public CustomApiClient(TwitterSession session) {
        super(session);
    }

    public APIService getApiService(){
        return getService(APIService.class);
    }
}
