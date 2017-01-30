package com.example.suman.weatherapp.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Suman on 1/9/2017.
 */

public class Current
{

    private String mIcon;
    private double mTemperature;
    private String mTimeZone;
    private long mTime;
    private double mHumidity;
    private double mPrecipChance;
    private String mSummary;

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public String getFormattedTime()
    {
        SimpleDateFormat formatter=new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime=new Date(getTime()*1000);
        String timeString=formatter.format(dateTime);
        return timeString;
    }
    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }


    public int getIconId()
    {
        return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public int getTemperature()
    {
        return (int) Math.abs(mTemperature);
    }

    public void setTemperature(double temperature) {
        mTemperature = (temperature-32)*5/9;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getPrecipChance() {
        return Math.round(mPrecipChance*100);
    }

    public void setPrecipChance(double precipChance) {
        mPrecipChance = precipChance;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
