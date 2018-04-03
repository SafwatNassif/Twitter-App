package com.example.safwat.twitterapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safwat.twitterapp.R;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Safwat on 4/3/2018.
 */

public class TweetAdapter  extends RecyclerView.Adapter<TweetAdapter.TweetHolder> {

    private ArrayList<Tweet> tweets;
    private Context context;
    private int Resource;
    public TweetAdapter(Context context, ArrayList<Tweet> tweets,int Resource) {
        this.tweets =tweets;
        this.context=context;
        this.Resource =Resource;
    }

    @Override
    public TweetHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(Resource,parent,false);
        return new TweetHolder(v);
    }

    @Override
    public void onBindViewHolder(TweetHolder holder, int position) {
        Picasso.with(context).load(tweets.get(position).user.profileImageUrl).into(holder.profile);
        holder.name.setText(tweets.get(position).user.name);
        holder.ScreenName.setText("@"+tweets.get(position).user.screenName);
        holder.Message.setText(tweets.get(position).text);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class TweetHolder extends RecyclerView.ViewHolder{
        CircleImageView profile ;
        TextView name ,ScreenName , Message;

        public TweetHolder(View itemView) {
            super(itemView);
            profile = (CircleImageView) itemView.findViewById(R.id.tweet_user_profile);
            name = (TextView) itemView.findViewById(R.id.tweet_name);
            ScreenName = (TextView) itemView.findViewById(R.id.tweet_screen_name);
            Message = (TextView) itemView.findViewById(R.id.tweet_message);
        }
    }
}
