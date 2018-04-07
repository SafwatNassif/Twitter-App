package com.example.safwat.twitterapp.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.safwat.twitterapp.Fragment.FollowerFragment;
import com.example.safwat.twitterapp.Presenter.FollowerPresenter;
import com.example.safwat.twitterapp.PresenterInterface.FollowerActivityPresenterInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.Utility;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class Followers extends AppCompatActivity implements FollowerActivityViewInterface {

    private CircleImageView profilePicture;
    private TextView userName;
    private User user;
    private FollowerActivityPresenterInterface followerActivityPresenterInterface;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        // define all View's in the Follower activity
        define();
        // swap to FollowerFragment to show all follower's
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_follower_container,new FollowerFragment()).commit();
     }


    private void define() {
        profilePicture =(CircleImageView) findViewById(R.id.profile_Menu);
        userName=(TextView) findViewById(R.id.user_name_title);
        toolbar =(Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        followerActivityPresenterInterface = new FollowerPresenter(this);
        followerActivityPresenterInterface.getUser();
    }

    // this method called after fetch user from twitter api client
    @Override
    public void SetImageAndName(User users) {
        this.user=users;
        // used sharedPreferences  to save {profile_url,banner_url, name}
        // to show it in navigationBar in Details Screen/Activity
        SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
        editor.putString("profile",user.profileImageUrl);
        editor.putString("banner",user.profileBannerUrl);
        editor.putString("name",user.name);
        editor.apply();
        profilePicture.setVisibility(View.VISIBLE);
        // load profile_picture to show it in toolbar
        Picasso.with(this).load(user.profileImageUrl).into(profilePicture);
        userName.setText(user.name);
    }


    // menu option using to setting Language {English/Arabic}
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting_it){
            followerActivityPresenterInterface.setLang();
        }
        return super.onOptionsItemSelected(item);
    }

    // when choose language we  need to recreated the activity
    @Override
    public void ReCreate() {
        recreate();
    }

}
