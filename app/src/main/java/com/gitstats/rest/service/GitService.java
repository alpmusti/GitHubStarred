package com.gitstats.rest.service;

import com.gitstats.rest.model.ProfileModel;
import com.gitstats.rest.model.HomeModel;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by ALP on 19.12.2015.
 */
public interface GitService {
    @GET("/users/{username}")
    public void getHomeDatas(@Path("username") String user, Callback<ProfileModel> response);

    @GET("/users/{user}/starred")
    public void getProfileDatas(@Path("user") String user,Callback<List<HomeModel>> response);
}
