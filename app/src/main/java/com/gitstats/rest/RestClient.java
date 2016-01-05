package com.gitstats.rest;

import com.gitstats.rest.service.GitService;

import retrofit.RestAdapter;

/**
 * Created by ALP on 19.12.2015.
 */
public class RestClient {
    private static final String BASE_URL = "https://api.github.com";
    private GitService gitService;

    public RestClient() {
        RestAdapter restAdapter = new RestAdapter
                .Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        gitService = restAdapter.create(GitService.class);
    }

    public GitService getGitService(){
        return gitService;
    }
}
