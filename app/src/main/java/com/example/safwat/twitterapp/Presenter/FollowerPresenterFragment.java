package com.example.safwat.twitterapp.Presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.safwat.twitterapp.Fragment.FollowerFragment;
import com.example.safwat.twitterapp.Model.TwitterFollower;
import com.example.safwat.twitterapp.Model.TwitterFollowerResponse;
import com.example.safwat.twitterapp.PresenterInterface.FollowerPresenterFragmentInterface;
import com.example.safwat.twitterapp.Service.CustomApiClient;
import com.example.safwat.twitterapp.View.FollowersViewInterface;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Safwat on 4/2/2018.
 */

public class FollowerPresenterFragment implements FollowerPresenterFragmentInterface, Callback<TwitterFollowerResponse> {
    private static final String LOG_CAT = FollowerPresenterFragment.class.getSimpleName();
    private Context context;
    private Activity main;
    private FollowersViewInterface viewInterface;
    private List<TwitterFollower> follower;

    public FollowerPresenterFragment(FollowerFragment view) {
        this.context = view.getContext();
        viewInterface = view;
        main =view.getActivity();
    }

    @Override
    public void getFollowers(){
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        Long id = twitterSession.getUserId();

        CustomApiClient customApiClient = new CustomApiClient(twitterSession,context);
        Call<TwitterFollowerResponse> followerList = customApiClient.getApiService().followerList(id);
        followerList.enqueue(this);
    }


    @Override
    public void onResponse(Call<TwitterFollowerResponse> call, Response<TwitterFollowerResponse> response) {
        TwitterFollowerResponse twitterResponse = response.body();
        follower = twitterResponse.getResults();
        if (follower != null){
            Log.e(LOG_CAT,"first name is : "+follower.get(0).getName());
            Log.e(LOG_CAT,"\nbio is :"+follower.get(0).getDescription());
            viewInterface.OnFetchFollowers(follower);
        }else{
            Log.e(LOG_CAT,"there is no followers");
            viewInterface.onEmptyFollowers();

        }
    }

    @Override
    public void onFailure(Call<TwitterFollowerResponse> call, Throwable t) {
        Log.e(LOG_CAT,"error in fetch followers : "+t.toString());
    }
}
