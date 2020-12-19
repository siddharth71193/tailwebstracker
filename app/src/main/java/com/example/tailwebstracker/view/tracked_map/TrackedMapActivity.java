package com.example.tailwebstracker.view.tracked_map;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.example.tailwebstracker.R;
import com.example.tailwebstracker.utils.CommonUtils;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class TrackedMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Bundle bundle;
    private ArrayList<Double> latList;
    private ArrayList<Double> longList;
    private ArrayList<LatLng> mPolyLinePoints = new ArrayList<>();
    private ImageView mClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracked_map);

        methodForFragmentSetUp();

        init();

        Intent intent = getIntent();
        bundle=intent.getExtras();

        if (bundle != null){
            latList = (ArrayList<Double>) getIntent().getSerializableExtra("Lat_List");
            longList = (ArrayList<Double>) getIntent().getSerializableExtra("Long_List");
        }

        for (int i=0; i<longList.size();i++ ){
            LatLng latLng = new LatLng(latList.get(i), longList.get(i));
            mPolyLinePoints.add(latLng);
        }

    }

    private void init(){
        mClose = findViewById(R.id.clearback);
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

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
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, Math.min(CommonUtils.getHeight(this),CommonUtils.getWidth(this)),Math.min(CommonUtils.getHeight(this),CommonUtils.getWidth(this)),100);
        mMap.animateCamera(cu);
    }

    protected Marker createMarker(double latitude, double longitude, String title) {  //create marker according to change location

        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }

    public void methodForFragmentSetUp(){
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(TrackedMapActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        plotLatLng();
    }
}