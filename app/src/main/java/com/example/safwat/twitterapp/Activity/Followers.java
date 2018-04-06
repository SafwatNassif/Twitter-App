package com.example.safwat.twitterapp.Activity;

import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.safwat.twitterapp.Fragment.FollowerFragment;
import com.example.safwat.twitterapp.Presenter.FollowerActivityPresenter;
import com.example.safwat.twitterapp.PresenterInterface.FollowerActivityPresenterInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import java.io.Serializable;

import de.hdodenhof.circleimageview.CircleImageView;

public class Followers extends AppCompatActivity implements Serializable,FollowerActivityViewInterface {

    private CircleImageView profilePicture;
    private TextView userName;
    private User user;
     private FollowerActivityPresenterInterface followerActivityPresenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        define();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_follower_container,new FollowerFragment()).commit();
     }

    private void define() {
        profilePicture =(CircleImageView) findViewById(R.id.profile_Menu);
        userName=(TextView) findViewById(R.id.user_name_title);
        followerActivityPresenterInterface = new FollowerActivityPresenter(this);
        followerActivityPresenterInterface.getUser();

    }

    @Override
    public void SetImageAndName(User users) {
        this.user=users;
        SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
        editor.putString("profile",user.profileImageUrl);
        editor.putString("banner",user.profileBannerUrl);
        editor.putString("name",user.name);
        editor.apply();
        Picasso.with(this).load(user.profileImageUrl).into(profilePicture);
        userName.setText(user.name);
    }


}
