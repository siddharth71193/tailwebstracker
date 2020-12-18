package com.example.tailwebstracker.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.tailwebstracker.R;
import com.example.tailwebstracker.view.dashboard.DashBoardActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent newIntent = new Intent(SplashActivity.this, DashBoardActivity.class);
        startActivity(newIntent);
    }
}