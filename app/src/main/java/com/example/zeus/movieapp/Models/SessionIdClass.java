package com.example.zeus.movieapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Zeus on 26-Mar-16.
 */
public class SessionIdClass {
    public boolean success;
    @SerializedName("session_id")
    public String sessionId;
}
