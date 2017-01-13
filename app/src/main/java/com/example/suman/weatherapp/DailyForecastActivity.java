package com.example.suman.weatherapp;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.BindView;

public class DailyForecastActivity extends ListActivity {
    private Day[] mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);

        Intent intent=getIntent();
        //String locationName=intent.getStringExtra(MainActivity.LOCATION_NAME);
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
       mDays= Arrays.copyOf(parcelables,parcelables.length,Day[].class);
        DayAdapter adapter=new DayAdapter(this,mDays);
        setListAdapter(adapter);

    }
}
