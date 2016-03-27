package com.example.zeus.movieapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tanvi on 26-03-2016.
 */
public class ApiClient {

    private static ApiInterface mService;
    public static ApiInterface getInterface()
    {
        if(mService==null) {
            Gson gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT).create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory
                            .create(gson)).build();


            mService = retrofit.create(ApiInterface.class);
        }   return mService;




    }



}
