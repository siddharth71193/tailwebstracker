package com.example.tailwebstracker.view.tracking;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.example.tailwebstracker.model.location_details.TrackDetails;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class TrackingHistoryViewModel extends AndroidViewModel {
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference dbReference;

    private MutableLiveData<List<TrackDetails>> trackDetails = new MutableLiveData<>();
    private MutableLiveData<Boolean> trackDetailsError = new MutableLiveData<>();

    public TrackingHistoryViewModel(@NonNull Application application) {
        super(application);
        FirebaseApp.initializeApp(application);
        firebaseFirestore = FirebaseFirestore.getInstance();
        dbReference = firebaseFirestore.collection("trackdetails");
    }

    public MutableLiveData<List<TrackDetails>> getTrackDetails(){
        return trackDetails;
    }

    public MutableLiveData<Boolean> getTrackDetailsError(){
        return trackDetailsError;
    }

    public void callGetTrackingData(){
        dbReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            trackDetails.setValue(new ArrayList<>());
                        } else {
                            List<TrackDetails> types = queryDocumentSnapshots.toObjects(TrackDetails.class);
                            trackDetails.setValue(types);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplication(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
