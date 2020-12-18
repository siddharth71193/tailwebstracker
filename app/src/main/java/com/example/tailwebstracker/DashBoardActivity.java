package com.example.tailwebstracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Map;

public class DashBoardActivity extends AppCompatActivity {
    private Button mYes;
    private Button mTrackingHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        init();
    }

    private void init(){
        mYes = findViewById(R.id.button);
        mTrackingHistory = findViewById(R.id.buttonStop);

        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, MapsActivity.class));
            }
        });

        mTrackingHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this,TrackingHistoryActivity.class));
            }
        });

    }
}