package com.example.safwat.twitterapp.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.safwat.twitterapp.Adapter.TweetAdapter;
import com.example.safwat.twitterapp.Presenter.DetailPresenterFragment;
import com.example.safwat.twitterapp.PresenterInterface.DetailPresenterFragmentInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.View.DetailViewInterface;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailsFragment extends Fragment implements DetailViewInterface, SwipeRefreshLayout.OnRefreshListener{
    private ImageView cover;
    private CircleImageView profilePicture;
    private TextView name;
    private DetailPresenterFragmentInterface presenter;
    private RecyclerView tweet_list;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayout Details_container ;
    private static final String LOG_CAT = DetailsFragment.class.getSimpleName();

    public DetailsFragment() {
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View RootView =inflater.inflate(R.layout.fragment_details, container, false);
        cover=(ImageView) RootView.findViewById(R.id.cover);
        profilePicture=(CircleImageView) RootView.findViewById(R.id.profile);
        name =(TextView) RootView.findViewById(R.id.UserName);
        tweet_list =(RecyclerView) RootView.findViewById(R.id.tweets_list);
        refreshLayout =(SwipeRefreshLayout) RootView.findViewById(R.id.details_refresh);
        Details_container=(LinearLayout) RootView.findViewById(R.id.details_container);

        Details_container.setVisibility(View.GONE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        tweet_list.setLayoutManager(mLayoutManager);

        presenter = new DetailPresenterFragment(this);
        onRefresh();
        return RootView;
    }


    @Override
    public void getDataFromBundle(List<Tweet> data) {
        Picasso.with(getContext()).load(data.get(0).user.profileBannerUrl).into(cover);
        Picasso.with(getContext()).load(data.get(0).user.profileImageUrl).into(profilePicture);
        name.setText(data.get(0).user.name);
        TweetAdapter adapter = new TweetAdapter(getContext(),new ArrayList<Tweet>(data),R.layout.tweet_item);
        tweet_list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        Details_container.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                presenter.getFollowerData();
            }
        },100);
    }
}
