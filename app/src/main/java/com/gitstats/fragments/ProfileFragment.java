package com.gitstats.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deneme.R;
import com.gitstats.activity.MainActivity;
import com.gitstats.adapters.ProfileAdapter;
import com.gitstats.app.App;
import com.gitstats.rest.model.ProfileModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ALP on 19.12.2015.
 */
public class ProfileFragment extends Fragment {
    private View view;
    private RecyclerView profileRecyclerView;
    private RecyclerView.LayoutManager profileLayoutManager;
    private List<ProfileModel> profileDatas;
    private ProfileAdapter profileAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileRecyclerView = (RecyclerView) view.findViewById(R.id.profile_recycler_view);
        profileRecyclerView.setHasFixedSize(true);
        profileLayoutManager = new LinearLayoutManager(this.getActivity());
        profileRecyclerView.setLayoutManager(profileLayoutManager);

        HomeBackgroundTask homeBackgroundTask = new HomeBackgroundTask();
        //run the AsyncTask
        homeBackgroundTask.execute();
        return this.view;
    }

    public class HomeBackgroundTask extends AsyncTask<Void, Void, Void> {
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
            profileDatas = new ArrayList<>();
            App.getRestClient().getGitService().getHomeDatas(MainActivity.getUsername(), new Callback<ProfileModel>() {
                @Override
                public void success(ProfileModel profileModel, Response response) {
                    Log.i("Retrofit veriler geldi", "Veriler alindi");
                    profileDatas.add(profileModel); //get JSON datas
                    profileAdapter = new ProfileAdapter(getActivity(), profileDatas);
                    profileRecyclerView.setAdapter(profileAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                Log.e("Retrofit error" , error.getLocalizedMessage());
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
