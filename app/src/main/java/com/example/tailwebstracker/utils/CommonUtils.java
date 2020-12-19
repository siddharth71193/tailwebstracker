package com.example.tailwebstracker.utils;

import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

public class CommonUtils {

    public static int getWidth(AppCompatActivity appCompatActivity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return width;
    }

    public static int getHeight(AppCompatActivity appCompatActivity){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        appCompatActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        return height;
    }
}
