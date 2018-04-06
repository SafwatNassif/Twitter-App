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
import android.widget.Toast;

import com.example.safwat.twitterapp.Adapter.FollowersAdapter;
import com.example.safwat.twitterapp.Model.TwitterFollower;
import com.example.safwat.twitterapp.Presenter.FollowerPresenterFragment;
import com.example.safwat.twitterapp.View.FollowerActivityViewInterface;
import com.example.safwat.twitterapp.PresenterInterface.FollowerPresenterFragmentInterface;
import com.example.safwat.twitterapp.R;
import com.example.safwat.twitterapp.View.FollowersViewInterface;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FollowerFragment extends Fragment implements
        FollowersViewInterface, SwipeRefreshLayout.OnRefreshListener, Runnable {

    private User user;
    private FollowerPresenterFragmentInterface followerPresenterFragmentInterface;
    private RecyclerView recyclerView;
    private FollowersAdapter adapter;
    private SwipeRefreshLayout refreshLayout;

    public FollowerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View RootView = inflater.inflate(R.layout.fragment_follower, container, false);
        Bundle bundle= getArguments();
        recyclerView =(RecyclerView) RootView.findViewById(R.id.follower_list);
        refreshLayout =(SwipeRefreshLayout) RootView.findViewById(R.id.refresh_follower);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setVisibility(View.GONE);
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        refreshLayout.setOnRefreshListener(this);

        followerPresenterFragmentInterface = new FollowerPresenterFragment(this);
        onRefresh();
        return RootView;
    }


    @Override
    public void OnFetchFollowers(List<TwitterFollower> followers) {
        ArrayList<TwitterFollower> arrayList = new ArrayList<>(followers);
        adapter = new FollowersAdapter(getContext(),R.layout.follower_item,arrayList,user);
        refreshLayout.setRefreshing(false);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEmptyFollowers() {
        Toast.makeText(getContext(), "There is no Followers", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        new Handler().postDelayed(this,1000);
    }

    @Override
    public void run() {
        followerPresenterFragmentInterface.getFollowers();
    }

}
