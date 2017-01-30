package com.example.suman.weatherapp;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suman.weatherapp.model.Day;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActivity extends AppCompatActivity {
    private Day[] mDays;
    @BindView(android.R.id.list)ListView dailyListView;
    @BindView(android.R.id.empty) TextView emptyTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        //String locationName=intent.getStringExtra(MainActivity.LOCATION_NAME);
        Parcelable[] parcelables=intent.getParcelableArrayExtra(MainActivity.DAILY_FORECAST);
        mDays= Arrays.copyOf(parcelables,parcelables.length,Day[].class);
        DayAdapter adapter=new DayAdapter(this,mDays);
        dailyListView.setAdapter(adapter);
        dailyListView.setEmptyView(emptyTextView);
        dailyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayOfTheWeek=mDays[position].getDayOfTheWeek();
                String conditions=mDays[position].getSummary();
                String highTemp=mDays[position].getTemperature()+"";
                String message=String.format("On %s the temperature will rise upto %s and it will be %s",dayOfTheWeek,highTemp,conditions);
                Toast.makeText(DailyForecastActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }


}
