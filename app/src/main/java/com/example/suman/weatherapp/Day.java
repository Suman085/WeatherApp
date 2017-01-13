package com.example.suman.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Suman on 1/11/2017.
 */

public class Day implements Parcelable{
    private String mIcon;
    private long mTime;
    private String mSummary;
    private String mTimeZone;
    private  double mTemperatureMax;

    public Day(){

    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }
    public long getTime()
    {
        return mTime;
    }
    public String getDayOfTheWeek(){
        SimpleDateFormat formatter=new SimpleDateFormat("EEEE");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateTime=new Date(mTime*1000);
        return formatter.format(dateTime);
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getTemperature()
    {
        return (int) Math.round(mTemperatureMax-32)*5/9;
    }

    public void setTemperature(double temperature) {
        mTemperatureMax = temperature;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mTime);
        dest.writeString(mSummary);
        dest.writeDouble(mTemperatureMax);
        dest.writeString(mIcon);
        dest.writeString(mTimeZone);
    }
    private Day(Parcel in){
        mTime=in.readLong();
        mSummary=in.readString();
        mTemperatureMax=in.readDouble();
        mIcon=in.readString();
        mTimeZone=in.readString();
    }
    public static final Creator<Day> CREATOR=new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel source) {
            return new Day(source);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };
    {

    }
}
