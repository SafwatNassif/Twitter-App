package com.example.safwat.twitterapp.Service;

import android.content.Context;

import com.example.safwat.twitterapp.Utility;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Safwat on 4/2/2018.
 */

public class CustomApiClient extends TwitterApiClient  {


    public CustomApiClient(TwitterSession session) {
        super(session);
    }

    public APIService getApiService(){
        return getService(APIService.class);
    }


}
