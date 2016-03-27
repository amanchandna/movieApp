package com.example.zeus.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zeus.movieapp.Models.Movie;
import com.example.zeus.movieapp.Models.MovieByGenreResults;
import com.example.zeus.movieapp.Networking.ApiClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieListByGenreActivity extends AppCompatActivity {
    ListView movieListView;
    MovieListAdapter adapter;
    ProgressDialog progressDialog;
    ArrayList<Movie> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list_by_genre);
        progressDialog=new ProgressDialog(MovieListByGenreActivity.this);
        progressDialog.setTitle("Fetching Movies List");
        progressDialog.setMessage("In progress...");

        Intent i=getIntent();
        int genreId=i.getIntExtra("genreId", 18);
        Call<MovieByGenreResults>
                movieListByGenreActivityCall=
                ApiClient.getApiInterface().getMovieListByGenre(genreId,MovieContract.LoginContract.API_KEY);
        progressDialog.show();
        movieListByGenreActivityCall.enqueue(new Callback<MovieByGenreResults>() {
            @Override
            public void onResponse(Call<MovieByGenreResults> call, Response<MovieByGenreResults> response) {
                progressDialog.dismiss();
                if(response.isSuccessful()){

                    movieListView=(ListView) findViewById(R.id.movieListByGenreListview);
                    MovieByGenreResults hell=response.body();

                    movieList=hell.genreMovies;

                    Log.i("Check",movieList.get(1).movieName);
                    adapter=new MovieListAdapter(MovieListByGenreActivity.this,movieList);
                    if(movieListView==null)
                    {
                        Toast.makeText(MovieListByGenreActivity.this, "Panga", Toast.LENGTH_SHORT).show();
                    }
                    else
                        movieListView.setAdapter(adapter);


                }else{
                    Toast.makeText(MovieListByGenreActivity.this, "Response unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieByGenreResults> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MovieListByGenreActivity.this, "call failed", Toast.LENGTH_SHORT).show();

            }
        });
    }
}
