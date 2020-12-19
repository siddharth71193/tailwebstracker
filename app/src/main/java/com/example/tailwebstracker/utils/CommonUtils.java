package com.example.tailwebstracker.utils;

import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        String[] splitDate = formattedDate.split("-");
        String ordinalValue = "";
        String monthFull = "";

        int dateLastNumber = Integer.parseInt(splitDate[0]);
        dateLastNumber = dateLastNumber%10;

        ordinalValue = getDayOfMonthSuffix(dateLastNumber);
        monthFull = getMonth(splitDate[1]);

        formattedDate = splitDate[0]+ordinalValue+" "+monthFull+ " "+splitDate[2];

        return formattedDate;
    }

    public static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    public static String getMonth(String month){
        if(month.equals("Jan")){
            return "January";
        } else if(month.equals("Feb")){
            return "February";
        } else if(month.equals("Mar")){
            return "March";
        } else if(month.equals("Apr")){
            return "April";
        }else if(month.equals("May")){
            return "May";
        } else if(month.equals("Jun")){
            return "June";
        }else if(month.equals("Jul")){
            return "July";
        } else if(month.equals("Aug")){
            return "August";
        } else if(month.equals("Sep")){
            return "September";
        }else if(month.equals("Oct")){
            return "October";
        } else if(month.equals("Nov")){
            return "November";
        } else {
            return "December";
        }
    }
}
