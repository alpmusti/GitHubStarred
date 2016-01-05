package com.gitstats.adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deneme.R;
import com.gitstats.rest.model.ProfileModel;

import java.util.List;

/**
 * Created by ALP on 25.12.2015.
 */
public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    public List<ProfileModel> NAMES_DATA;
    private static Activity activity;

    public ProfileAdapter(Activity activity, List<ProfileModel> NAMES_DATA) {
        this.activity = activity;
        this.NAMES_DATA = NAMES_DATA;
    }

    @Override
    public ProfileAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_profile_cardview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProfileAdapter.ViewHolder holder, int position) {
        //Load datas from NAMES_DATA
        holder.homePersonName.setText(NAMES_DATA.get(position).getName());
        holder.homeCompanyName.setText("Company : " + NAMES_DATA.get(position).getCompany());
        holder.homeBlogName.setText(NAMES_DATA.get(position).getBlog());
        Glide.with(holder.homePersonPhoto.getContext())
                .load(NAMES_DATA.get(position).getAvatarUrl())
                .fitCenter()
                .into(holder.homePersonPhoto);
    }

    @Override
    public int getItemCount() {
        return NAMES_DATA.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView homePersonPhoto;
        TextView homePersonName;
        TextView homeCompanyName;
        TextView homeBlogName;

        public ViewHolder(View view) {
            super(view);
            homePersonPhoto = (ImageView) view.findViewById(R.id.home_repo_photo);
            homePersonName = (TextView) view.findViewById(R.id.home_repo_name);
            homeCompanyName = (TextView) view.findViewById(R.id.profile_person_company);
            homeBlogName = (TextView) view.findViewById(R.id.profile_person_blog);

            homePersonPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    new AlertDialog.Builder(activity)
                            .setTitle("Change Profile Picture")
                            .setMessage("Are you really want to change your profile picture ?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with change profile picture
                                    int PICK_IMAGE_REQUEST = 1;
                                    Intent intent = new Intent();
                                    // Show only images, no videos or anything else
                                    intent.setType("image/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    // Always show the chooser (if there are multiple options available)
                                    activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                                }
                            })
                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                    dialog.cancel();
                                }
                            })
                            .show();
                }
            });
        }
    }
}

