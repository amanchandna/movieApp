package com.example.zeus.movieapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeus on 27-Mar-16.
 */
public class Movie {

    @SerializedName("id")
    public int movieId;
    @SerializedName("original_title")
    public String movieName;
    @SerializedName("popularity")
    public double moviePopularity;
}
