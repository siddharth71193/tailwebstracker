package com.example.tailwebstracker.view.tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.tailwebstracker.R;
import com.example.tailwebstracker.adapter.TrackDetailsAdapter;
import com.example.tailwebstracker.model.location_details.TrackDetails;

import java.util.List;

public class TrackingHistoryActivity extends AppCompatActivity {
    private TrackingHistoryViewModel trackingHistoryViewModel;
    private RecyclerView recyclerView;
    private TrackDetailsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_history);
        init();
    }

    private void init(){
        recyclerView = findViewById(R.id.recyclerId);
        trackingHistoryViewModel = ViewModelProviders.of(this).get(TrackingHistoryViewModel.class);
        observeViewModel();
        trackingHistoryViewModel.callGetTrackingData();
    }

    private void observeViewModel(){
        trackingHistoryViewModel.getTrackDetails().observe(this, new Observer<List<TrackDetails>>() {
            @Override
            public void onChanged(List<TrackDetails> trackDetails) {
                setUpAdapter(trackDetails);
            }
        });
    }

    private void setUpAdapter(List<TrackDetails> mArrayList){
        //set layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new TrackDetailsAdapter(this, mArrayList);
        recyclerView.setAdapter(mAdapter);
    }
}