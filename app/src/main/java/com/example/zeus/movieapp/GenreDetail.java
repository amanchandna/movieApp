package com.example.zeus.movieapp;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tanvi on 26-03-2016.
 */
public class GenreDetail {

    @SerializedName("id")
     private int Genre_id;
    @SerializedName("name")
     private String Genre_name;

    public int getGenre_id() {
        return Genre_id;
    }

    public void setGenre_id(int genre_id) {
        Genre_id = genre_id;
    }

    public String getGenre_name() {
        return Genre_name;
    }

    public void setGenre_name(String genre_name) {
        Genre_name = genre_name;
    }
}
