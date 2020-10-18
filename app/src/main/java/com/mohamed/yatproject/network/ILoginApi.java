package com.mohamed.yatproject.network;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ILoginApi {

    @GET("login")
    public Call<LoginResponse> login(@Query("email") String userEmail,
                                  @Query("password") String password);
}
