package com.example.zeus.movieapp.Networking;

import com.example.zeus.movieapp.Models.MovieByGenreResults;
import com.example.zeus.movieapp.Models.RawRequestToken;
import com.example.zeus.movieapp.Models.SessionIdClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Zeus on 26-Mar-16.
 */
public interface ApiInterface {

    @GET("authentication/token/new")
    Call<RawRequestToken> getRawRequestToken(@Query("api_key") String API_KEY);

    @GET("authentication/token/validate_with_login")
    Call<RawRequestToken> validateRequestToken(@Query("api_key") String API_KEY,
                                               @Query("request_token") String REQUEST_TOKEN,
                                               @Query("username") String username,
                                               @Query("password") String password);
    @GET("authentication/session/new")
    Call<SessionIdClass> getSessionId(@Query("api_key") String API_KEY,
                                              @Query("request_token") String REQUEST_TOKEN);

    @GET("genre/{id}/movies")
    Call<MovieByGenreResults> getMovieListByGenre(@Path("id") int genreId,@Query("api_key") String API_KEY);

}
