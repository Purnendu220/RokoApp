package com.rokoapp.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rokoapp.R;
import com.rokoapp.adapter.TripHistoryAdapter;
import com.rokoapp.manageApi.ApiManager;
import com.rokoapp.manageApi.FailureCodes;
import com.rokoapp.manageApi.ResponseProgressListener;
import com.rokoapp.model.response.TripHistorySubModel;

import java.util.ArrayList;
import java.util.List;

public class PreviousTrips extends Fragment implements ResponseProgressListener {

    private RecyclerView recyclerPreviousTrip;

    private List<TripHistorySubModel> tripList = new ArrayList<>();
    private TripHistoryAdapter tripAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous_trips, container, false);
        initViews(view);
        FragmentActivity activity = getActivity();

        recyclerPreviousTrip.setHasFixedSize(true);
        tripAdapter = new TripHistoryAdapter(getContext(), tripList);
        recyclerPreviousTrip.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerPreviousTrip.setItemAnimator(new DefaultItemAnimator());
        recyclerPreviousTrip.setAdapter(tripAdapter);


        ApiManager.getUserBookingHistory(activity, tripList,"history", this);

        return view;
    }

    private void initViews(View view) {
        recyclerPreviousTrip = view.findViewById(R.id.recyclerPreviousTrip);
    }

    @Override
    public void onResponseInProgress() {

    }

    @Override
    public void onResponseCompleted(int i) {
        if (i==1){
            tripAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onResponseCompletedWithData(int i, Object data) {

    }

    @Override
    public void onResponseFailed(FailureCodes code) {

    }
}
