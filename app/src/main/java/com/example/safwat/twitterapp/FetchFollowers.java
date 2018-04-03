package com.example.safwat.twitterapp;

 import android.content.Context;
 import android.util.Log;
 import android.widget.Toast;

 import com.example.safwat.twitterapp.Model.TwitterFollower;
 import com.example.safwat.twitterapp.Model.TwitterFollowerResponse;
 import com.example.safwat.twitterapp.Presenter.FollowerPresenter;
 import com.example.safwat.twitterapp.View.FollowersViewInterface;
 import com.twitter.sdk.android.core.Twitter;
 import com.twitter.sdk.android.core.models.User;
import java.util.List;

 import retrofit2.Call;
 import retrofit2.Callback;
 import retrofit2.Response;


/**
 * Created by Safwat on 4/2/2018.
 */

public class FetchFollowers implements Callback<TwitterFollowerResponse>{
    private FollowerPresenter presenter;
    private Context context;
    private String LOG_CAT =FetchFollowers.class.getSimpleName();
    private List<TwitterFollower> follower;

    public FetchFollowers(Context context, FollowerPresenter p) {
        this.context=context;
        presenter = p;
    }

    @Override
    public void onResponse(Call<TwitterFollowerResponse> call, Response<TwitterFollowerResponse> response) {
        TwitterFollowerResponse twitterResponse = response.body();
        follower = twitterResponse.getResults();
        if (follower != null){
            Log.e(LOG_CAT,"first name is : "+follower.get(0).getName());
            Log.e(LOG_CAT,"\nbio is :"+follower.get(0).getDescription());
            presenter.AfterFetchFollowers(follower);
        }else{
            Log.e(LOG_CAT,"there is no followers");
            presenter.IfEmptyFollowers();
        }
    }

    @Override
    public void onFailure(Call<TwitterFollowerResponse> call, Throwable t) {
        Toast.makeText(context,"erorrrrrrrrrrrrrre",Toast.LENGTH_LONG).show();
        Log.e(LOG_CAT,"error in fetch followers : "+t.toString());

    }
}
