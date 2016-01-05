package com.gitstats.adapters;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.deneme.R;
import com.gitstats.fragments.StatisticsFragment;
import com.gitstats.rest.model.HomeModel;

import java.util.List;

/**
 * Created by ALP on 25.12.2015.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private static List<HomeModel> NAMES_DATA;
    private static FragmentActivity activity;
    public static int location;

    public HomeAdapter(FragmentActivity activity, List<HomeModel> NAMES_DATA) {
        this.activity = activity;
        this.NAMES_DATA = NAMES_DATA;
        if(NAMES_DATA.isEmpty()){
            Toast.makeText(activity,"You don't have starred repository!!",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_cardview_layout, parent, false);

        return new ViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onBindViewHolder(HomeAdapter.ViewHolder holder, int position) {
        //Lod data from NAMES_DATA
            holder.homeRepoName.setText(String.valueOf(NAMES_DATA.get(position).getName()));
            holder.homeStars.setText(String.valueOf(NAMES_DATA.get(position).getStargazersCount()));
            holder.homeForks.setText(String.valueOf(NAMES_DATA.get(position).getForksCount()));
            Glide.with(activity)
                    .load(NAMES_DATA.get(position).getOwner().getAvatarUrl())
                    .into(holder.homeRepoPhoto);
    }

    @Override
    public int getItemCount() {
        return NAMES_DATA.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homeRepoPhoto;
        TextView homeRepoName;
        TextView homeStars;
        TextView homeForks;

        public ViewHolder(View view) {
            super(view);
            homeRepoPhoto = (ImageView) view.findViewById(R.id.home_repo_photo);
            homeRepoName = (TextView) view.findViewById(R.id.home_repo_name);
            homeStars = (TextView) view.findViewById(R.id.home_stars_count);
            homeForks = (TextView) view.findViewById(R.id.home_forks_count);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Open statistics for this repository.
                    location = getAdapterPosition();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, new StatisticsFragment())
                            .addToBackStack("null").commit();
                }
            });
        }
    }

    public static HomeModel getNAMES_DATA() {
        return NAMES_DATA.get(location); //return location of this repository
    }
}

