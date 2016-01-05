package com.gitstats.app;

import android.app.Application;

import com.gitstats.rest.RestClient;

/**
 * Created by ALP on 19.12.2015.
 */
public class App extends Application {

    private static RestClient restClient;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
    }

    public static RestClient getRestClient() {  return restClient; }
}
