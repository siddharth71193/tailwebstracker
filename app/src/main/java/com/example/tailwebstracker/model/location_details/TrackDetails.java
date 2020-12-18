package com.example.tailwebstracker.model.location_details;

import java.util.ArrayList;

public class TrackDetails {
    private long id;
    private String totalTime;
    private ArrayList<Double> latList;
    private ArrayList<Double> longList;
    private String date;

    public TrackDetails(){

    }
    public TrackDetails(long id, String totalTime, ArrayList<Double> latList, ArrayList<Double> longList, String date) {
        this.id = id;
        this.totalTime = totalTime;
        this.latList = latList;
        this.longList = longList;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}