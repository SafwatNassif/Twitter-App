package com.example.safwat.twitterapp.View;

import com.example.safwat.twitterapp.Model.TwitterFollower;

import java.util.List;

/**
 * Created by Safwat on 4/2/2018.
 */

public interface FollowersViewInterface {
    void SetImageAndName(String name,String url);
    void OnFetchFollowers(List<TwitterFollower> followers);
    void onEmptyFollowers();
}
