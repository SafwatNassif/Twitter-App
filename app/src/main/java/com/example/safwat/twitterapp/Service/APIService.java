package com.example.safwat.twitterapp.Service;

import com.example.safwat.twitterapp.Model.TwitterFollowerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Safwat on 4/2/2018.
 */

public interface APIService {
    // service that using to get list of follower's
    @GET("/1.1/followers/list.json")
    Call<TwitterFollowerResponse> followerList(@Query("user_id") Long id);

}
