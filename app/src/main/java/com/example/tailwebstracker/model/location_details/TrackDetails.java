package com.example.tailwebstracker.model.location_details;

import java.util.ArrayList;

public class TrackDetails {
    private long id;
    private ArrayList<Double> latList;
    private ArrayList<Double> longList;

    public TrackDetails(){

    }
    public TrackDetails(long id, ArrayList<Double> latList, ArrayList<Double> longList) {
        this.id = id;
        this.latList = latList;
        this.longList = longList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ArrayList<Double> getLatList() {
        return latList;
    }

    public void setLatList(ArrayList<Double> latList) {
        this.latList = latList;
    }

    public ArrayList<Double> getLongList() {
        return longList;
    }

    public void setLongList(ArrayList<Double> longList) {
        this.longList = longList;
    }
}
