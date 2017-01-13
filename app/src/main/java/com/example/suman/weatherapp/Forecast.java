package com.example.suman.weatherapp;

/**
 * Created by Suman on 1/11/2017.
 */

public class Forecast {
    Hour[] mHourlyForecast;
    Day[] mDailyForecast;
    Current mCurrent;
    public static int getIconId(String mIconString) {
        int iconId = R.drawable.clear_day;
        if (mIconString.equals("clear-day")) {
            iconId = R.drawable.clear_day;
        } else if (mIconString.equals("clear-night")) {
            iconId = R.drawable.clear_night;
        } else if (mIconString.equals("rain")) {
            iconId = R.drawable.rain;
        } else if (mIconString.equals("snow")) {
            iconId = R.drawable.snow;
        } else if (mIconString.equals("sleet")) {
            iconId = R.drawable.sleet;
        } else if (mIconString.equals("wind")) {
            iconId = R.drawable.wind;
        } else if (mIconString.equals("fog")) {
            iconId = R.drawable.fog;
        } else if (mIconString.equals("cloudy")) {
            iconId = R.drawable.cloudy;
        } else if (mIconString.equals("partly-cloudy-day")) {
            iconId = R.drawable.partly_cloudy;
        } else if (mIconString.equals("partly-cloudy-night")) {
            iconId = R.drawable.cloudy_night;

        }
        return iconId;
    }
    public Hour[] getHourlyForecast() {
        return mHourlyForecast;
    }

    public void setHourlyForecast(Hour[] hourlyForecast) {
        mHourlyForecast = hourlyForecast;
    }

    public Day[] getDailyForecast() {
        return mDailyForecast;
    }

    public void setDailyForecast(Day[] dailyForecast) {
        mDailyForecast = dailyForecast;
    }

    public Current getCurrent() {
        return mCurrent;
    }

    public void setCurrent(Current current) {
        mCurrent = current;
    }
}
