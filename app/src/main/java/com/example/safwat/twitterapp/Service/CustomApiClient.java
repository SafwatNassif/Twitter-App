package com.example.safwat.twitterapp.Service;

import android.content.Context;
import android.util.Log;

import com.example.safwat.twitterapp.Utility;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.internal.network.OkHttpClientHelper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Safwat on 4/2/2018.
 */

public class CustomApiClient extends TwitterApiClient  {

    private final static String LOG_CAT=CustomApiClient.class.getSimpleName();

    // custom api client to get follower's and cache the api to work offline
    public CustomApiClient(TwitterSession session,Context context) {
        super(session ,provideOkHttpClient(context));
    }

    // to cache data  offline we need to pass client to TwitterApiClient
    // so i create okHttpClient that also help to cache api using interceptor callback
    private static OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor()
                        .setLevel(HttpLoggingInterceptor.Level.BASIC))
                .addInterceptor(provideOfflineCacheInterceptor(context))
                .addNetworkInterceptor(provideCacheNetworkInterceptor(context))
                .cache(provideCache(context))
                .build();

        return okHttpClient;

    }

    // create cache file that will store api data
    private static Cache provideCache(Context context) {
        Cache cache = null;
        try {
            cache = new Cache(new File(context.getCacheDir(),"twitter-cache"),30*1024*1024);
        }catch (Exception e){
            Log.e(LOG_CAT,"error in create cache file");
        }
        return cache;
    }

    private static Interceptor provideCacheNetworkInterceptor(Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response =chain.proceed(chain.request());
                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(2,TimeUnit.MINUTES)
                        .build();
                response =response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control",cacheControl.toString())
                        .build();
                return response;
            }
        };
    }

    // in case offline and we need api data it provided from cache-file
    private static Interceptor provideOfflineCacheInterceptor(final Context context) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if(!Utility.IsNetworkAvailable(context)){
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();
                    request = request.newBuilder()
                            .removeHeader("Pragma")
                            .cacheControl(cacheControl)
                            .build();

                }
                return chain.proceed(request);
            }
        };
    }

    // return ApiService that will used to get follower data
    public APIService getApiService(){
        return getService(APIService.class);
    }


}
