package com.indira.usedbooks;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Manish on 09-04-2017.
 */

public class UsedbooksApplication extends Application{
    private Retrofit mRetrofit;

    public static UsedbooksApplication getInstance() {
        return sUsedbooksApplication;
    }

    private static UsedbooksApplication sUsedbooksApplication;
    private final String API_URL = "https://blxusedbooks.000webhostapp.com/";
    @Override
    public void onCreate() {
        super.onCreate();
        sUsedbooksApplication = this;
    }


    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit;
    }
}
