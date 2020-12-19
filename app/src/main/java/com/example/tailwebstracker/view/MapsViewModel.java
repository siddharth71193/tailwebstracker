package com.example.tailwebstracker.view;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.tailwebstracker.model.location_details.TrackDetails;
import com.example.tailwebstracker.utils.CommonUtils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MapsViewModel extends AndroidViewModel {

    private FirebaseFirestore firebaseFirestore;
    private CollectionReference dbReference;
    private MutableLiveData<Boolean> hasSuccessfullyCompleted = new MutableLiveData<>();

    public MapsViewModel(@NonNull Application application) {
        super(application);
        FirebaseApp.initializeApp(application);
        firebaseFirestore = FirebaseFirestore.getInstance();
        dbReference = firebaseFirestore.collection("trackdetails");
    }

    public MutableLiveData<Boolean> getHasSuccessfullyCompleted(){
        return hasSuccessfullyCompleted;
    }

    public void insertToDb(ArrayList<LatLng> latLngs) {
        ArrayList<Double> latsList = new ArrayList<>();
        ArrayList<Double> lngList = new ArrayList<>();
        long id = new java.util.Date().getTime();

        for (int i = 0; i < latLngs.size(); i++) {
            latsList.add(latLngs.get(i).latitude);
            lngList.add(latLngs.get(i).longitude);
        }

        TrackDetails trackDetails = new TrackDetails(id, MapsActivity.t, latsList, lngList, CommonUtils.getCurrentDate());

        dbReference.add(trackDetails).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplication(), "Successfully added", Toast.LENGTH_SHORT).show();
                hasSuccessfullyCompleted.setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                hasSuccessfullyCompleted.setValue(false);
            }
        });
    }

}
