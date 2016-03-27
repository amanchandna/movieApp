package com.example.zeus.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Zeus on 27-Mar-16.
 */
public class MovieByGenreResults {
   @SerializedName("results")
   public ArrayList<Movie> genreMovies;
}
