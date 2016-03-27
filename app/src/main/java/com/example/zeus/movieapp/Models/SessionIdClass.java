package com.example.zeus.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Zeus on 26-Mar-16.
 */
public class SessionIdClass implements Serializable {
    public boolean success;
    public String userName;
    @SerializedName("session_id")
    public String sessionId;
}
