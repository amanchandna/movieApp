package com.example.zeus.movieapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zeus.movieapp.Models.RawRequestToken;
import com.example.zeus.movieapp.Models.SessionIdClass;
import com.example.zeus.movieapp.Networking.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    SessionIdClass sessionIdObj;
    public String REQUEST_TOKEN="";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String sessionId;
    ProgressDialog progressDialog;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClickedId=item.getItemId();
        if(itemClickedId==R.id.logoutMenuItem){
            editor.putString("sessionId", "");
            editor.commit();
            Intent i=new Intent();
            i.setClass(MainActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp=getSharedPreferences("login", Context.MODE_PRIVATE);
        editor=sp.edit();



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
                progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setTitle("Logging in");
                progressDialog.setMessage("In progress...");
                progressDialog.show();
                sessionId=sp.getString("sessionId", "");


                if(sessionId!=""){
                    progressDialog.dismiss();
                    SessionIdClass sessionObj=new SessionIdClass();
                    sessionObj.sessionId=sessionId;
                    sessionObj.userName=sp.getString("username","");
                    Intent i=new Intent();
                    Toast.makeText(MainActivity.this, "Welcome "+sessionObj.userName,
                            Toast.LENGTH_LONG).show();
                    i.setClass(MainActivity.this, MovieListByGenreActivity.class);
                    startActivity(i);

                    //start Tanvi's Activity here
                }else{
                    rawrequestTokenCall.enqueue(new Callback<RawRequestToken>() {
                        @Override
                        public void onResponse(Call<RawRequestToken> call, Response<RawRequestToken> response) {
                            progressDialog.dismiss();
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
                                        Call<RawRequestToken> validatedTokenCall=ApiClient.getApiInterface()
                                                .validateRequestToken(MovieContract.LoginContract.API_KEY,
                                                        REQUEST_TOKEN,username,password);

                                        progressDialog.show();


                                        validatedTokenCall.enqueue(new Callback<RawRequestToken>() {
                                            @Override
                                            public void onResponse(Call<RawRequestToken> call, Response<RawRequestToken> response) {
                                                progressDialog.dismiss();
                                                if(response.isSuccessful()){

                                                    RawRequestToken validatedToken=response.body();

                                                    Call<SessionIdClass> sessionIdCall=ApiClient
                                                            .getApiInterface()
                                                            .getSessionId(MovieContract.LoginContract.API_KEY,
                                                                    REQUEST_TOKEN);
                                                    sessionIdCall.enqueue(new Callback<SessionIdClass>() {
                                                        @Override
                                                        public void onResponse(Call<SessionIdClass> call, Response<SessionIdClass> response) {
                                                            if(response.isSuccessful()){
                                                                sessionIdObj =response.body();
                                                                sessionIdObj.userName=username;
                                                                editor.putString("sessionId", sessionIdObj.sessionId);
                                                                editor.putString("username", username);
                                                                editor.commit();
                                                                Intent i=new Intent();

                                                                //start Tanvi's Activity here
                                                                Toast.makeText(MainActivity.this, "Tanvi ki activity start karo",
                                                                        Toast.LENGTH_LONG).show();
                                                                i.setClass(MainActivity.this, MovieListByGenreActivity.class);
                                                                startActivity(i);

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

                                                }else{
                                                    Toast.makeText(MainActivity.this, "Incorrect username/password"
                                                            , Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RawRequestToken> call, Throwable t) {
                                                progressDialog.dismiss();
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
                            progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Token call 1 failure"
                                    , Toast.LENGTH_SHORT).show();
                        }
                    });
                }



            }
        });


    }
}
