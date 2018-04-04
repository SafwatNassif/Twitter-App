package com.example.safwat.twitterapp.Activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safwat.twitterapp.Adapter.FollowersAdapter;
import com.example.safwat.twitterapp.Model.TwitterFollower;
import com.example.safwat.twitterapp.Presenter.FollowerPresenter;
import com.example.safwat.twitterapp.PresenterInterface.FollowerPresenterInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.View.FollowersViewInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Followers extends AppCompatActivity implements FollowersViewInterface, SwipeRefreshLayout.OnRefreshListener {

    private CircleImageView profilePicture;
    private TextView userName;
    private FollowerPresenterInterface followerPresenterInterface;
    private RecyclerView recyclerView;
    private FollowersAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        profilePicture =(CircleImageView) findViewById(R.id.profile_Menu);
        userName=(TextView) findViewById(R.id.user_name_title);
        recyclerView =(RecyclerView) findViewById(R.id.follower_list);
        refreshLayout =(SwipeRefreshLayout) findViewById(R.id.refresh_follower);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this);
        followerPresenterInterface = new FollowerPresenter(this);
        followerPresenterInterface.getUser();
     }

    @Override
    public void SetImageAndName(String name, String url) {
        userName.setText(name);
        Picasso.with(this).load(url).into(profilePicture);
        followerPresenterInterface.getFollowers();
    }

    @Override
    public void OnFetchFollowers(List<TwitterFollower> followers) {
        ArrayList<TwitterFollower> arrayList = new ArrayList<>(followers);
        adapter = new FollowersAdapter(this,R.layout.follower_item,arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onEmptyFollowers() {
        Toast.makeText(this, "There is no Followers", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        followerPresenterInterface.getUser();

    }
}
