package com.example.zeus.movieapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeus.movieapp.Models.RawRequestToken;
import com.example.zeus.movieapp.Models.SessionIdClass;
import com.example.zeus.movieapp.Models.ValidatedToken;
import com.example.zeus.movieapp.Networking.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SessionIdClass sessionIdClass;
    public String REQUEST_TOKEN="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sp.edit();
        String sessionId=sp.getString("sessionId", "");

        if(sessionId!=""){
            Intent i=new Intent();
            i.putExtra("sessionId",sessionId);
            //start Tanvi's Activity here
            Toast.makeText(MainActivity.this, "Tanvi ki activity start karo",
                    Toast.LENGTH_LONG).show();
        }

        else{
            Button guestLogin=(Button)findViewById(R.id.guestButton);
            Button userLogin=(Button)findViewById(R.id.userLoginButton);


            guestLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            userLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Call<RawRequestToken> rawrequestTokenCall=
                            ApiClient.getApiInterface()
                                    .getRawRequestToken(MovieContract.LoginContract.API_KEY);
                    rawrequestTokenCall.enqueue(new Callback<RawRequestToken>() {
                        @Override
                        public void onResponse(Call<RawRequestToken> call, Response<RawRequestToken> response) {
                            if(response.isSuccessful()&&
                                    response.body().success==true){
                                RawRequestToken rawRequestToken=response.body();
                                REQUEST_TOKEN=rawRequestToken.reqToken;
                                AlertDialog.Builder bd=new AlertDialog.Builder(MainActivity.this);
                                bd.setTitle("Login");
                                bd.setTitle("Enter Login Details-");
                                View v=getLayoutInflater().inflate(R.layout.user_login_dialog,null);
                                final EditText userNameEditText=(EditText)v.findViewById(R.id.userNameEditText);
                                final EditText passwordEditText=(EditText)v.findViewById(R.id.passwordEditText);
                                bd.setView(v);
                                bd.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        final String username=userNameEditText.getText().toString();
                                        String password=passwordEditText.getText().toString();
                                        Call<ValidatedToken> validatedTokenCall=ApiClient.getApiInterface()
                                                .validateRequestToken(MovieContract.LoginContract.API_KEY,
                                                        REQUEST_TOKEN,username,password);
                                        validatedTokenCall.enqueue(new Callback<ValidatedToken>() {
                                            @Override
                                            public void onResponse(Call<ValidatedToken> call, Response<ValidatedToken> response) {
                                                if(response.isSuccessful()){
                                                    ValidatedToken validatedToken=response.body();
                                                    if(validatedToken.success==true){
                                                        //Get a session ID now
//                                                    Toast.makeText(MainActivity.this,
//                                                            "Log In SuccessFul: "+username
//                                                            , Toast.LENGTH_SHORT).show();
                                                        Call<SessionIdClass> sessionIdCall=ApiClient
                                                                .getApiInterface()
                                                                .getSessionId(MovieContract.LoginContract.API_KEY,
                                                                        REQUEST_TOKEN);
                                                        sessionIdCall.enqueue(new Callback<SessionIdClass>() {
                                                            @Override
                                                            public void onResponse(Call<SessionIdClass> call, Response<SessionIdClass> response) {
                                                                if(response.isSuccessful()){
                                                                    sessionIdClass=response.body();

                                                                    editor.putString("sessionId",sessionIdClass.sessionId);
                                                                    editor.commit();
                                                                    Intent i=new Intent();
                                                                    i.putExtra("sessionId",sessionIdClass.sessionId);
                                                                    //start Tanvi's Activity here
                                                                    Toast.makeText(MainActivity.this, "Tanvi ki activity start karo",
                                                                            Toast.LENGTH_LONG).show();



                                                                }else{
                                                                    Toast.makeText(MainActivity.this, "Response 3 unsuccessful"
                                                                            , Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                            @Override
                                                            public void onFailure(Call<SessionIdClass> call, Throwable t) {
                                                                Toast.makeText(MainActivity.this, "Session ID get Failure"
                                                                        , Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                                    }

                                                }else{
                                                    Toast.makeText(MainActivity.this, "Incorrect username/password"
                                                            , Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ValidatedToken> call, Throwable t) {
                                                Toast.makeText(MainActivity.this, "Token validation failure"
                                                        , Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                    }
                                });
                                bd.create().show();
                            }
                            else{
                                Toast.makeText(MainActivity.this, "Response 1 unsuccessful"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<RawRequestToken> call, Throwable t) {
                            Toast.makeText(MainActivity.this, "Token call 1 failure"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }

    }
}
