package com.example.zeus.movieapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by tanvi on 26-03-2016.
 */
public interface ApiInterface {

    @GET("genre/movie/list"+Apikeyclass.API_KEY)
    Call<Genre> getGenre();




}
