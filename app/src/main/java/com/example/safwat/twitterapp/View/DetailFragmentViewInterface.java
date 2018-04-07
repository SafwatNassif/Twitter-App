package com.example.safwat.twitterapp.View;

import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by Safwat on 4/3/2018.
 */

public interface DetailFragmentViewInterface {
    void getTweetList(List<Tweet> data);
    void EmptyTweet();
}
