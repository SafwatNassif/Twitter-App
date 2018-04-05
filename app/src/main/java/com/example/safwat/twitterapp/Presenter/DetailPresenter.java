package com.example.safwat.twitterapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.safwat.twitterapp.Activity.Details;
import com.example.safwat.twitterapp.PresenterInterface.DetailPresenterInterface;
import com.example.safwat.twitterapp.Service.CustomApiClient;
import com.example.safwat.twitterapp.View.DetailViewInterface;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import java.util.List;

import retrofit2.Call;


/**
 * Created by Safwat on 4/3/2018.
 */

public class DetailPresenter extends Callback<List<Tweet>> implements DetailPresenterInterface {
    private Context context;
    private DetailViewInterface detailViewInterface;
    private Activity details;
    private Long id;
    private final static String LOG_CAT =DetailPresenter.class.getSimpleName();
    private Intent i;

    public DetailPresenter(Details details) {
        this.context = details;
        detailViewInterface = details;
        this.details =  details;
    }

    @Override
    public void getFollowerData() {
        i = details.getIntent();
        id =i.getLongExtra("id",-1);
        Log.e(LOG_CAT,"id is "+id);
        getTweets();

    }


    public void getTweets(){
        TwitterApiClient apiClient = new CustomApiClient(TwitterCore.getInstance()
                .getSessionManager().getActiveSession(),context);
//      TwitterApiClient apiClient = TwitterCore.getInstance().getApiClient();
        StatusesService statusesService = apiClient.getStatusesService();
        Call<List<Tweet>> call = statusesService
                .userTimeline(id,i.getStringExtra("screen_name"),
                        10,null,null,null,null,
                        null,null);
        call.enqueue(this);
    }



    @Override
    public void success(Result<List<Tweet>> result) {
        Log.e(LOG_CAT,"tweet message is : \n"+result.data.get(0).text+
                "tweeting screenName : "+result.data.get(0).user.screenName);
        detailViewInterface.getDataFromBundle(result.data);
    }

    @Override
    public void failure(TwitterException exception) {
        Log.e(LOG_CAT,"tweet error failure : \n"+exception.getMessage());
    }
}
