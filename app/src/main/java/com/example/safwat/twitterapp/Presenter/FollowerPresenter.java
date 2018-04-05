package com.example.safwat.twitterapp.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.safwat.twitterapp.FetchFollowers;
import com.example.safwat.twitterapp.Activity.Followers;
import com.example.safwat.twitterapp.Model.TwitterFollower;
import com.example.safwat.twitterapp.Model.TwitterFollowerResponse;
import com.example.safwat.twitterapp.PresenterInterface.FollowerPresenterInterface;
import com.example.safwat.twitterapp.Service.CustomApiClient;
import com.example.safwat.twitterapp.View.FollowersViewInterface;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.ListService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Safwat on 4/2/2018.
 */

public class FollowerPresenter implements FollowerPresenterInterface, Callback<User> {
    private static final String LOG_CAT = FollowerPresenter.class.getSimpleName();
    private Context context;
    private FollowersViewInterface viewInterface;
    private TwitterApiClient twitterApiClient;
    private FetchFollowers fetchFollowers;

    public FollowerPresenter(Followers view) {
        this.context = view;
        viewInterface = view;
    }

    @Override
    public  void getUser() {
        twitterApiClient = new CustomApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession(),context);
//        twitterApiClient = TwitterCore.getInstance().getApiClient();
        Call<User> userCall = twitterApiClient.getAccountService().verifyCredentials(true,
                false, true);
        userCall.enqueue(this);
    }
    @Override
    public void getFollowers(){
        TwitterSession twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        TwitterAuthToken authtoken = twitterSession.getAuthToken();
        String token = authtoken.token;
        Long id = twitterSession.getUserId();

        fetchFollowers = new FetchFollowers(context,this);
        CustomApiClient customApiClient = new CustomApiClient(twitterSession,context);
        Call<TwitterFollowerResponse> followerList = customApiClient.getApiService().followerList(id);
        followerList.enqueue(fetchFollowers);
    }

    @Override
    public void AfterFetchFollowers(List<TwitterFollower> followers) {
        viewInterface.OnFetchFollowers(followers);
    }

    @Override
    public void IfEmptyFollowers() {
        viewInterface.onEmptyFollowers();
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            viewInterface.SetImageAndName(user.name,user.profileImageUrl);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Log.e(LOG_CAT,"error while get your user data of toolbar");
    }
}
