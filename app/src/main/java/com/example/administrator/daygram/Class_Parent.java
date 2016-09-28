package com.example.administrator.daygram;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/25.
 */
public class Class_Parent implements Serializable{
    public static final byte YEAR=0;
    public static final byte MONTH = 1;
    public static final byte DAY=2;
    public static final byte WEEK=3;

    private int year;
    private int month;
    private int day;
    private int week;


    public Class_Parent(int y, int m, int d, int w){
        year = y ;
        month = m;
        day = d;
        week=w;

    }

    public static ArrayList<Object> data;   //静态成员被用作ListView的数据源，可被各个类调用

    public int getDate(byte YEAR_MONTH_DAY_WEEK) {
        if(YEAR_MONTH_DAY_WEEK==YEAR)
            return year;
        else if(YEAR_MONTH_DAY_WEEK==MONTH)
            return month;
        else if(YEAR_MONTH_DAY_WEEK==DAY)
            return day;
        else if(YEAR_MONTH_DAY_WEEK==WEEK)
            return week;
        else
            return 0;
    }

    public String getWeekAsString(Context context){
        switch (week){
            case 1:
                return context.getString(R.string.Sunday);
            case 2:
                return context.getString(R.string.Monday);
            case 3:
                return context.getString(R.string.Tuesday);
            case 4:
                return context.getString(R.string.Wednesday);
            case 5:
                return context.getString(R.string.Thursday);
            case 6:
                return context.getString(R.string.Friday);
            case 7:
                return context.getString(R.string.Saturday);
            default:
                return "";
        }
    }

    public String getMonthAsString(){
        switch (month){
            case 1:
                return "January";
            case 2:
                return "February";
            case 3:
                return "March";
            case 4:
                return "April";
            case 5:
                return "May";
            case 6:
                return "June";
            case 7:
                return "July";
            case 8:
                return "August";
            case 9:
                return "September";
            case 10:
                return "October";
            case 11:
                return "November";
            case 12:
                return "December";
            default:
                return "";
        }
    }
    /*public String getDateAsString(byte YEAR_MONTH_DAY_WEEK){
        if(YEAR_MONTH_DAY_WEEK==YEAR)
            return String.valueOf(year);
        else if(YEAR_MONTH_DAY_WEEK==MONTH)
            return String.valueOf(month);
        else if(YEAR_MONTH_DAY_WEEK==DAY)
            return day;
        else if(YEAR_MONTH_DAY_WEEK==WEEK)
            return week;
        else
            return 0;
    }*/

    public boolean ifSunday(){
        if(getDate(Class_Parent.WEEK)==1)
            return true;
        else return false;
    }

}
