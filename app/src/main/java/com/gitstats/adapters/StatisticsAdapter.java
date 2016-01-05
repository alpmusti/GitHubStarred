package com.gitstats.adapters;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deneme.R;
import com.gitstats.rest.model.HomeModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by ALP on 31.12.2015.
 */
public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.ViewHolder> {
    private HomeModel NAMES_DATA;
    private static FragmentActivity activity;
    private ObjectAnimator forksObjectAnimator;
    private ObjectAnimator starsObjectAnimator;

    public StatisticsAdapter(FragmentActivity activity, HomeModel NAMES_DATA) {
        this.activity = activity;
        this.NAMES_DATA = NAMES_DATA;
    }

    @Override
    public StatisticsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_statistics_cardview_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onBindViewHolder(StatisticsAdapter.ViewHolder holder, int position) {
        //Load datas from NAMES_DATA
        final int total = NAMES_DATA.getForksCount() + NAMES_DATA.getStargazersCount();
        final int starsPercentage = (NAMES_DATA.getStargazersCount() * 101) / total;
        final int forksPercentage = (NAMES_DATA.getForksCount() * 101) / total;
        Glide.with(activity)
                .load(NAMES_DATA.getOwner().getAvatarUrl())
                .into(holder.repoPhoto);
        holder.repoName.setText(NAMES_DATA.getName());
        holder.progressBarText1.setText(" Forks");
        holder.progressBarText2.setText(" Stars");
        holder.progressBarForks.setMax(100);
        holder.progressBarStars.setMax(100);
        holder.text_count.setText("Forks : " + String.valueOf(NAMES_DATA.getForksCount())
                + "\tStars: " + String.valueOf(NAMES_DATA.getStargazersCount())
                + "\tTotals : " + String.valueOf(total));
        holder.forksPercentage.setText(String.valueOf(forksPercentage) + "%");
        holder.starsPercentage.setText(String.valueOf(starsPercentage) + "%");
        setProgressAnimation(holder.progressBarStars, holder.progressBarForks, forksPercentage, starsPercentage);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView repoPhoto;
        ImageButton iconShare;
        TextView repoName,
                progressBarText1, progressBarText2,
                text_count,
                forksPercentage, starsPercentage;
        ProgressBar progressBarForks, progressBarStars;
        RelativeLayout relativeLayout;
        LinearLayout linearLayout;

        public ViewHolder(View view) {
            super(view);
            repoPhoto = (ImageView) view.findViewById(R.id.statistics_repo_photo);
            repoName = (TextView) view.findViewById(R.id.statistic_text);
            progressBarText1 = (TextView) view.findViewById(R.id.progressbar_text);
            progressBarText2 = (TextView) view.findViewById(R.id.progressbar_text2);
            text_count = (TextView) view.findViewById(R.id.counts_text);
            progressBarForks = (ProgressBar) view.findViewById(R.id.progressBar);
            progressBarStars = (ProgressBar) view.findViewById(R.id.progressBar2);
            starsPercentage = (TextView) view.findViewById(R.id.stars_percentage);
            forksPercentage = (TextView) view.findViewById(R.id.forks_percentage);
            iconShare = (ImageButton) view.findViewById(R.id.statistics_share_button);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear_layout);

            iconShare.setOnClickListener(new View.OnClickListener() {
                Bitmap myBitmap;

                @Override
                public void onClick(View v) {
                    linearLayout.post(new Runnable() {
                        public void run() {
                            new AlertDialog.Builder(activity)
                                    .setTitle("Share")
                                    .setMessage("Are you really want to share this repository's statistics ?")
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            //take screenshot
                                            myBitmap = captureScreen(linearLayout);

                                            try {
                                                if (myBitmap != null) {
                                                    //save image to SD card
                                                    saveImage(myBitmap);
                                                }

                                            } catch (IOException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .show();
                        }
                    });
                }
            });
        }

        private Bitmap captureScreen(View v) {
            //Capture screen in LinearLayout
            Bitmap screenshot = null;
            v.setDrawingCacheEnabled(true);
            v.buildDrawingCache(true);
            try {

                if (v != null) {
                    screenshot = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(screenshot);
                    v.draw(canvas);
                }

            } catch (Exception e) {
                Log.d("ScreenShot capturing ", "Failed to capture screenshot because:" + e.getMessage());
            }

            return screenshot;
        }

        private void saveImage(Bitmap bitmap) throws IOException {
            //Save screenshot captured
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 40, bytes);
            //Save this screenshot this directory
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "share.png");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            initShareIntent();   // Share this image
        }

        private void initShareIntent() {
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            //get the image
            shareIntent.putExtra(Intent.EXTRA_STREAM,
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + "share.png")));
            shareIntent.putExtra(Intent.EXTRA_TEXT, text_count.getText().toString());
            //get the statistics text
            shareIntent.setType("image/PNG");
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            activity.startActivity(Intent.createChooser(shareIntent, "Share..."));
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setProgressAnimation(ProgressBar starProgress, ProgressBar forkProgress, int maxForks, int maxStars) {
        //Animation for loading progressbar
        forksObjectAnimator = ObjectAnimator.ofInt(forkProgress, "progress", 0, maxForks);
        forksObjectAnimator.setDuration(1000);
        forksObjectAnimator.start();
        starsObjectAnimator = ObjectAnimator.ofInt(starProgress, "progress", 0, maxStars);
        starsObjectAnimator.setDuration(1000);
        starsObjectAnimator.start();
    }

}
