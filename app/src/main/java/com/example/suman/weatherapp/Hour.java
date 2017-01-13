package com.example.suman.weatherapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Suman on 1/11/2017.
 */

public class Hour implements Parcelable {
    private String mIcon;
    private Double mTemperature;
    private long mTime;
    private String mSummary;

    public Hour(){}

    public String getTimeZone() {
        return mTimeZone;
    }
    public String getHour(){
        SimpleDateFormat formatter=new SimpleDateFormat("h a");
        formatter.setTimeZone(TimeZone.getTimeZone(mTimeZone));
        Date dateT=new Date(mTime*1000);
        return formatter.format(dateT);
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    private String mTimeZone;

    public int getIconId(){
        return Forecast.getIconId(mIcon);
    }

    public int getTemperature() {
        return (int) Math.round((mTemperature-32)*5/9);
    }

    public void setTemperature(Double temperature) {
        mTemperature = temperature;
    }

    public long getTime() {
        return mTime;
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

    public String getIcon() {

        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSummary);
        dest.writeDouble(mTemperature);
        dest.writeString(mIcon);
        dest.writeLong(mTime);
        dest.writeString(mTimeZone);
    }

    private Hour(Parcel in){
        mSummary=in.readString();
        mTemperature=in.readDouble();
        mIcon=in.readString();
        mTime=in.readLong();
        mTimeZone=in.readString();
    }
    public static final Creator<Hour> CREATOR=new Creator<Hour>(){

        @Override
        public Hour createFromParcel(Parcel source) {
            return new Hour(source);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };
}
