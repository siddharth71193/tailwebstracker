package com.example.tailwebstracker.view;

import androidx.fragment.app.FragmentActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

import com.example.tailwebstracker.model.LocalLocation;
import com.example.tailwebstracker.service.LocationService;
import com.example.tailwebstracker.R;
import com.example.tailwebstracker.utils.Constants;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private ArrayList<LatLng> mPolyLinePoints = new ArrayList<>();
    private Chronometer mChronometer;
    private Button mStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        init();

        startService(new Intent(this, LocationService.class));

        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(mLocationReceiver,
                new IntentFilter(Constants.INTENT_FILTERS.LOCATION_BROADCAST));
    }

    private void init(){
        mChronometer = findViewById(R.id.chronometer);
        mStop = findViewById(R.id.stopTrackingId);
        mChronometer.start();

        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopChronometer();
            }
        });
    }

    private void stopChronometer(){
        mChronometer.setBase(SystemClock.elapsedRealtime());
        mChronometer.stop();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    protected BroadcastReceiver mLocationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            LocalLocation location = (LocalLocation) intent.getExtras().getSerializable("location");
            Log.e(TAG, "onReceive: "+ location.getLat()+" "+location.getLng());
            mPolyLinePoints.add(new LatLng(location.getLat(),location.getLng()));
            plotLatLng();
        }
    };

    private void plotLatLng(){
        mMap.clear();
        int n = mPolyLinePoints.size();

        if(n > 1) {
            for (int i = 0; i < n-1; i++) {
                LatLng src = mPolyLinePoints.get(i);
                LatLng dest = mPolyLinePoints.get(i + 1);

                // mMap is the Map Object
                Polyline line = mMap.addPolyline(
                        new PolylineOptions().add(
                                new LatLng(src.latitude, src.longitude),
                                new LatLng(dest.latitude,dest.longitude)
                        ).width(10).color(Color.BLUE).geodesic(true)
                );
            }
            zoomToFit();
            createMarker(mPolyLinePoints.get(0).latitude,mPolyLinePoints.get(0).longitude,"Start");
            createMarker(mPolyLinePoints.get(n-1).latitude,mPolyLinePoints.get(n-1).longitude,"End");
        } else {

        }
    }

    private void zoomToFit(){
        LatLngBounds.Builder b = new LatLngBounds.Builder();
        for (int i = 0;i < mPolyLinePoints.size();i++) {
            b.include(mPolyLinePoints.get(i));
        }
        LatLngBounds bounds = b.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Math.min(getHeight(),getWidth()),Math.min(getHeight(),getWidth()),100);
        mMap.animateCamera(cu);
    }

    private int getWidth(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    private int getHeight(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }

    protected Marker createMarker(double latitude, double longitude, String title) {  //create marker according to change location

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }
}