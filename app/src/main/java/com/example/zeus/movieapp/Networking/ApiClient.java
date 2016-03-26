package com.example.zeus.movieapp.Networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeus on 26-Mar-16.
 */
public class ApiClient {
    private static ApiInterface mService;

    public static ApiInterface getApiInterface(){
        if(mService==null){
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mService=retrofit.create(ApiInterface.class);
        }
        return mService;
    }
}
