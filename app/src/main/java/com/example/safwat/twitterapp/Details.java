package com.example.safwat.twitterapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.safwat.twitterapp.Adapter.TweetAdapter;
import com.example.safwat.twitterapp.Presenter.DetailPresenter;
import com.example.safwat.twitterapp.PresenterInterface.DetailPresenterInterface;
import com.example.safwat.twitterapp.View.DetailViewInterface;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity implements DetailViewInterface{

    private ImageView cover;
    private CircleImageView profilePicture;
    private TextView name;
    private DetailPresenterInterface presenter;
    private RecyclerView tweet_list;
    private static final String LOG_CAT = Details.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        cover=(ImageView) findViewById(R.id.cover);
        profilePicture=(CircleImageView) findViewById(R.id.profile);
        name =(TextView) findViewById(R.id.UserName);
        tweet_list =(RecyclerView) findViewById(R.id.tweets_list);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        tweet_list.setLayoutManager(mLayoutManager);

        presenter = new DetailPresenter(this);
        presenter.getFollowerData();

    }

    @Override
    public void getDataFromBundle(List<Tweet> data) {
        Picasso.with(this).load(data.get(0).user.profileBannerUrl).into(cover);
        Picasso.with(this).load(data.get(0).user.profileImageUrl).into(profilePicture);
        name.setText(data.get(0).user.name);
        TweetAdapter adapter = new TweetAdapter(this,new ArrayList<Tweet>(data),R.layout.tweet_item);
        tweet_list.setAdapter(adapter);
    }
}
