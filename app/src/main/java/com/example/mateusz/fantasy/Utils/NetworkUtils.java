package com.example.mateusz.fantasy.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mateusz on 19.08.2017.
 */

public class NetworkUtils {

    public static final String BASE_URL = "http://fantasypl.c0.pl/";

    public static Gson getGson(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    public static Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();
    }

}
