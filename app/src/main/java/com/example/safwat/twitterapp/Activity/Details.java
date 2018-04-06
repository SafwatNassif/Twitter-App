package com.example.safwat.twitterapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import com.example.safwat.twitterapp.Fragment.DetailsFragment;
import com.example.safwat.twitterapp.R;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class Details extends AppCompatActivity {

    private ImageView banner;
    private CircleImageView profile;
    private SharedPreferences sharedPreferences;
    private String profileUrl,bannerUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        define();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.details_refresh_fragment,new DetailsFragment()).commit();
    }

    private void define() {
        banner =(ImageView) findViewById(R.id.account_banner);
        profile=(CircleImageView) findViewById(R.id.account_profile);
        sharedPreferences= getSharedPreferences("user",MODE_PRIVATE);
        profileUrl = sharedPreferences.getString("profile","");
        bannerUrl=sharedPreferences.getString("banner","");
        Picasso.with(this).load(profileUrl).into(profile);
        Picasso.with(this).load(bannerUrl).into(banner);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
