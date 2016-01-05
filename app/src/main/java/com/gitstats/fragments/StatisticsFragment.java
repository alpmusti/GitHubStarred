package com.gitstats.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.deneme.R;
import com.gitstats.adapters.HomeAdapter;
import com.gitstats.adapters.StatisticsAdapter;

/**
 * Created by ALP on 31.12.2015.
 */
public class StatisticsFragment extends Fragment {
    private View view;
    private RecyclerView statisticsRecyclerView;
    private StatisticsAdapter statisticsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_statistics, container, false);
        statisticsRecyclerView = (RecyclerView) view.findViewById(R.id.statistics_recycler_view);
        statisticsRecyclerView.setHasFixedSize(true);
        statisticsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        statisticsAdapter = new StatisticsAdapter(getActivity(), HomeAdapter.getNAMES_DATA());
        statisticsRecyclerView.setAdapter(statisticsAdapter);

        return this.view;
    }
}
