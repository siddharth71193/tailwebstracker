package com.example.tailwebstracker.view.tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.tailwebstracker.R;
import com.example.tailwebstracker.model.location_details.TrackDetails;

import java.util.List;

public class TrackingHistoryActivity extends AppCompatActivity {
    private TrackingHistoryViewModel trackingHistoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_history);
        init();
    }

    private void init(){
        trackingHistoryViewModel = ViewModelProviders.of(this).get(TrackingHistoryViewModel.class);
        observeViewModel();
        trackingHistoryViewModel.callGetTrackingData();
    }

    private void observeViewModel(){
        trackingHistoryViewModel.getTrackDetails().observe(this, new Observer<List<TrackDetails>>() {
            @Override
            public void onChanged(List<TrackDetails> trackDetails) {

            }
        });
    }
}