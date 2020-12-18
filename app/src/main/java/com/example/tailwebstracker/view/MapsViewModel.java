package com.example.tailwebstracker.view;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.tailwebstracker.model.location_details.TrackDetails;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MapsViewModel extends AndroidViewModel {

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference dbReference;

    public MapsViewModel(@NonNull Application application) {
        super(application);
        FirebaseApp.initializeApp(application);
        firebaseFirestore = FirebaseFirestore.getInstance();
        dbReference = firebaseFirestore.collection("trackdetails");
    }

    public void insertToDb(ArrayList<LatLng> latLngs){
        ArrayList<Double> latsList = new ArrayList<>();
        ArrayList<Double> lngList = new ArrayList<>();
        long id = new java.util.Date().getTime();

        for(int i = 0;i < latLngs.size();i++){
            latsList.add(latLngs.get(i).latitude);
            lngList.add(latLngs.get(i).longitude);
        }

        TrackDetails trackDetails = new TrackDetails(id,latsList,lngList);

        dbReference.add(trackDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
