package com.example.safwat.twitterapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safwat.twitterapp.Activity.Details;
 import com.example.safwat.twitterapp.Model.TwitterFollower;
import com.example.safwat.twitterapp.R;
import com.squareup.picasso.Picasso;
 import com.twitter.sdk.android.core.models.User;

import java.io.Serializable;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Safwat on 4/2/2018.
 */

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.holder>  {


    private Context context;
    private int layout;
    private ArrayList<TwitterFollower> arrayList;

    public FollowersAdapter(Context context,int Resource,ArrayList<TwitterFollower> list){
        this.context=context;
        this.layout=Resource;
        this.arrayList=list;
    }


    @Override
    public holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layout,parent,false);
        return new holder(v);
    }

    @Override
    public void onBindViewHolder(holder holder, final int position) {
        Picasso.with(context).load(arrayList.get(position).getProfilePictureUrl()).into(holder.profile);
        holder.handle.setText("@"+arrayList.get(position).getScreenName());
        holder.name.setText(arrayList.get(position).getName());

        if(arrayList.get(position).getDescription()!="")
            holder.bio.setText(arrayList.get(position).getDescription());
        else
            holder.bio.setVisibility(View.GONE);

        holder.item_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, Details.class);
                i.putExtra("screen_name",arrayList.get(position).getScreenName());
                i.putExtra("id",arrayList.get(position).getId());
                i.putExtra("banner",arrayList.get(position).getProfileBannerUrl());
                context.startActivity(i);

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class holder extends RecyclerView.ViewHolder{
        CardView item_container;
        CircleImageView profile;
        TextView name,handle,bio;
        public holder(View itemView) {
            super(itemView);
            item_container=(CardView) itemView.findViewById(R.id.item_container);
            profile=(CircleImageView) itemView.findViewById(R.id.follower_profile);
            name=(TextView) itemView.findViewById(R.id.follower_name);
            handle=(TextView) itemView.findViewById(R.id.follower_handle);
            bio=(TextView) itemView.findViewById(R.id.follower_bio);
        }
    }
}
