package com.example.safwat.twitterapp.PresenterInterface;

import com.example.safwat.twitterapp.Model.TwitterFollower;

import java.util.List;

/**
 * Created by Safwat on 4/2/2018.
 */

public interface FollowerPresenterInterface {
    void getUser();
    void getFollowers();
    void AfterFetchFollowers(List<TwitterFollower> followers);
    void IfEmptyFollowers();
}
