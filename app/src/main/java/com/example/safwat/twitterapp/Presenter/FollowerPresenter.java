package com.example.safwat.twitterapp.Presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.util.Log;

import com.example.safwat.twitterapp.Activity.Followers;
import com.example.safwat.twitterapp.PresenterInterface.FollowerActivityPresenterInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.Service.CustomApiClient;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.User;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by safwat on 4/5/2018.
 */

public class FollowerPresenter implements FollowerActivityPresenterInterface, Callback<User> {
    private TwitterApiClient twitterApiClient;
    private Context context;
    private FollowerActivityViewInterface fView;
    private String LOG_CAT = FollowerPresenter.class.getSimpleName();
    private Dialog dialog;
    private Activity activity;

    public FollowerPresenter(Followers followers) {
        context =followers;
        fView = followers;
        activity =followers;
    }

    // get user that oAuth in this app and cache profile , banner and name to show in toolbar
    @Override
    public void getUser() {
        twitterApiClient = new CustomApiClient(TwitterCore.getInstance().getSessionManager().getActiveSession(),context);
        Call<User> userCall = twitterApiClient.getAccountService().verifyCredentials(true,
                false, true);
        userCall.enqueue(this);
    }

    //show dialog to user to choose which language he request
    //and tells the follower activity  recreate the activity with new language
    @Override
    public void setLang() {
        String[] listItem = {"English","العربية"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(R.string.choose_lang));
        builder.setSingleChoiceItems(listItem, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i==0){
                    setLangType("en");
                    dialog.dismiss();
                    fView.ReCreate();
                }else{
                    setLangType("ar");
                    dialog.dismiss();
                    fView.ReCreate();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }

    // save language that choose from dialog in setting menu
    private void setLangType(String l) {
        Locale locale = new Locale(l);
        Locale.setDefault(locale);
        Configuration configuration =activity.getBaseContext().getResources().getConfiguration();
        configuration.locale=locale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale);
        }

        activity.getBaseContext().getResources()
                .updateConfiguration(configuration,activity.getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor editor = activity.getBaseContext().getSharedPreferences("setting",Context.MODE_PRIVATE).edit();
        editor.putString("lang",l);
        editor.apply();

    }

    // after fetch user data we need to pass the data to Follower activity to show in toolbar
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
