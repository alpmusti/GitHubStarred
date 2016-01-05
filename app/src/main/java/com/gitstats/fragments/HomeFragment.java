package com.gitstats.fragments;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.deneme.R;
import com.gitstats.activity.MainActivity;
import com.gitstats.adapters.HomeAdapter;
import com.gitstats.app.App;
import com.gitstats.rest.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ALP on 19.12.2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeFragment extends Fragment {
    private View view;
    private RecyclerView homeRecyclerView;
    private HomeAdapter homeAdapter;
    private static List<HomeModel> homeModel;
    private TextView emptyText;
    private ImageView emptyIcon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);
        homeRecyclerView = (RecyclerView) view.findViewById(R.id.home_recycler_view);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        emptyIcon = (ImageView) view.findViewById(R.id.empty_icon);

        ProfileBackgroundTask profileBackgroundTask = new ProfileBackgroundTask();
        //run the AsyncTask
        profileBackgroundTask.execute();

        return this.view;
    }

    public class ProfileBackgroundTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            homeModel = new ArrayList<>();
            App.getRestClient().getGitService().getProfileDatas(MainActivity.getUsername(), new Callback<List<HomeModel>>() {
                @Override
                public void success(List<HomeModel> homeModels, Response response) {
                    homeModel = homeModels; //get the JSON datas
                    if (!homeModel.isEmpty()) {
                        //If no repository starred delete empty text and image
                        ((ViewManager)emptyText.getParent()).removeView(emptyText);
                        ((ViewManager)emptyIcon.getParent()).removeView(emptyIcon);
                    } else {

                    }
                    homeAdapter = new HomeAdapter(getActivity(), homeModel);
                    homeRecyclerView.setAdapter(homeAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("Retrofit Error", error.getLocalizedMessage());
                }
            });

            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}




