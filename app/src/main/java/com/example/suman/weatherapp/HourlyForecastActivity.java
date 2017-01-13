package com.example.suman.weatherapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyForecastActivity extends AppCompatActivity {
    @BindView(R.id.hour_list)ListView mListView;
    Forecast mForecast;
    Hour[] mHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.HOURLY_FORECAST);
        mHours= Arrays.copyOf(parcelables,parcelables.length,Hour[].class);
        HourAdapter adapter=new HourAdapter(this,mHours);
        mListView.setAdapter(adapter);
    }
}
