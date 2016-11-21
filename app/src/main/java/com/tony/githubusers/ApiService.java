package com.tony.githubusers;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Tony on 18/11/2016.
 */

public interface ApiService {
    @GET("/users/{user}")
    Call<ResponseBody> getUser(@Path("user") String user);
}
