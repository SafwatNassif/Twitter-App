package com.example.safwat.twitterapp.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.safwat.twitterapp.Activity.Followers;
import com.example.safwat.twitterapp.PresenterInterface.FollowerActivityPresenterInterface;
import com.example.safwat.twitterapp.Service.CustomApiClient;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by safwat on 4/5/2018.
 */

public class FollowerActivityPresenter implements FollowerActivityPresenterInterface, Callback<User> {
    private TwitterApiClient twitterApiClient;
    private Context context;
    private FollowerActivityViewInterface fView;
    private String LOG_CAT = FollowerActivityPresenter.class.getSimpleName();

    public FollowerActivityPresenter(Followers followers) {
        context =followers;
        fView = followers;
    }
    @Override
    public void getUser() {
        twitterApiClient = new CustomApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession(),context);
        Call<User> userCall = twitterApiClient.getAccountService().verifyCredentials(true,
                false, true);
        userCall.enqueue(this);
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        User user = response.body();
        fView.SetImageAndName(user);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e(LOG_CAT,"error while get your user data for toolbar");
    }
}
