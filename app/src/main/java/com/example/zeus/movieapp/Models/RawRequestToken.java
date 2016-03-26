package com.example.zeus.movieapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeus on 26-Mar-16.
 */
public class RawRequestToken {
    public boolean success;
    @SerializedName("request_token")
    public String reqToken;
}
