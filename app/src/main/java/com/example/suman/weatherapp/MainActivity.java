package com.example.suman.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.suman.weatherapp.model.Current;
import com.example.suman.weatherapp.model.Day;
import com.example.suman.weatherapp.model.Forecast;
import com.example.suman.weatherapp.model.Hour;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,LocationListener, GoogleApiClient.OnConnectionFailedListener {
    public static final String TAG=MainActivity.class.getSimpleName();
    public static final String DAILY_FORECAST="DAILY FORECAST";
    public static final String HOURLY_FORECAST="HOURLY_FORECAST";
    public static final String PLACE_NAME = "PLACE_NAME";
    private Forecast mForecast;
    private String timezone;
    private LatLng location2;
    @BindView(R.id.iconImageView) ImageView mIconImageView;
    @BindView(R.id.locationLabel) TextView mLocationLabel;
    @BindView(R.id.timeLabel) TextView mTimeLabel;
    @BindView(R.id.tempTextView)TextView mTempTextView;
    @BindView(R.id.humidityLabel) TextView mHumidityLabel;
    @BindView(R.id.humidityValue)TextView mHumidityValue;
    @BindView(R.id.precipLabel)TextView mPrecipLabel;
    @BindView(R.id.precipValue) TextView mPrecipValue;
    @BindView(R.id.summaryLabel)TextView mSummaryLabel;
    @BindView(R.id.refreshButton)ImageView mRefreshButton;
    @BindView(R.id.progressBar)ProgressBar mProgressBar;
    @BindView(R.id.hourly_button)Button mHourlyButton;
    @BindView(R.id.daily_button)Button mDailyButton;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mLocationRequest=new LocationRequest()
                .setInterval(15*1000)
                .setFastestInterval(5*1000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if(mGoogleApiClient!=null){
            mGoogleApiClient.connect();
            Log.e(TAG,"Hlelo world");
        }

        mProgressBar.setVisibility(View.INVISIBLE);
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForecast(location2.latitude,location2.longitude);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mGoogleApiClient!=null){
        mGoogleApiClient.connect();
    }
    }

    private void getForecast(double latitude, double longitude) {
        String apiKey="776177a499a6b2b49f8f057572df3a21";
        String forecastUrl="https://api.darksky.net/forecast/"+apiKey+"/"+latitude+","+longitude;

        if(isNetworkAvailable()) {
            toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(forecastUrl).build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toggleRefresh();
                        }
                    });
                    try {
                        String jsonData=response.body().string();
                        if (response.isSuccessful())
                        {
                            mForecast = parseForecastDetails(jsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateDisplay();
                                }
                            });

                        }
                        else {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e)
                    {
                       Log.e(TAG,"exception caught",e);
                    }
                    catch (JSONException e)
                    {
                        Log.e(TAG,"exception caught",e);
                    }

                }
            });
        }
        else
        {
            AlertDialogFragment alertDialogFragment=new AlertDialogFragment();
            alertDialogFragment.show(getFragmentManager(),"Network unavailable");
            Toast.makeText(this,"Network Unavailable",Toast.LENGTH_LONG);
        }
    }

    private Forecast parseForecastDetails(String jsonData) throws JSONException {
    Forecast forecast =new Forecast();
        forecast.setCurrent(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));
        forecast.setDailyForecast(getDailyForecast(jsonData));
        return forecast;
    }
    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast=new JSONObject(jsonData);
         timezone=forecast.getString("timezone");
        JSONObject currently=forecast.getJSONObject("currently");
        Current current =new Current();
        current.setHumidity(currently.getDouble("humidity"));
        current.setTime(currently.getLong("time"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setPrecipChance(currently.getDouble("precipProbability"));
        current.setIcon(currently.getString("icon"));
        current.setSummary(currently.getString("summary"));
        current.setTimeZone(timezone);
        return current;
    }
    private Day[] getDailyForecast(String jsonData) throws  JSONException{
        JSONObject forecast=new JSONObject(jsonData);
        String timezone=forecast.getString("timezone");
            JSONObject daily=forecast.getJSONObject("daily");
            JSONArray data=daily.getJSONArray("data");
            Day[] days=new Day[data.length()];
        for(int i=0;i<data.length();i++)
        {
         JSONObject jsonDay=data.getJSONObject(i);
            Day day=new Day();
            day.setTime(jsonDay.getLong("time"));
            day.setIcon(jsonDay.getString("icon"));
            day.setSummary(jsonDay.getString("summary"));
            day.setTemperature(jsonDay.getDouble("temperatureMax"));
            day.setTimeZone(timezone);
            days[i]=day;
        }
            return days;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException{
        JSONObject forecast=new JSONObject(jsonData);
        String timezone=forecast.getString("timezone");
        JSONObject hourly=forecast.getJSONObject("hourly");
        JSONArray data=hourly.getJSONArray("data");
        Hour[] hours=new Hour[data.length()];
        for(int i=0;i<data.length();i++)
        {
            JSONObject jsonHour=data.getJSONObject(i);
            Hour hour=new Hour();
            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timezone);
            hours[i]=hour;
        }
                return hours;
    }


    private void toggleRefresh() {
        if(mRefreshButton.getVisibility()==View.VISIBLE){
        mRefreshButton.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }
    else
        {
            mRefreshButton.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private void updateDisplay() {
        Current current= mForecast.getCurrent();
        mTempTextView.setText(current.getTemperature()+"");
        mHumidityValue.setText(current.getHumidity()+"");
        mLocationLabel.setText(current.getTimeZone());
        mIconImageView.setImageResource(current.getIconId());
        mTimeLabel.setText("At "+ current.getFormattedTime()+" it will be");
        mPrecipValue.setText(current.getPrecipChance()+"");
        mSummaryLabel.setText(current.getSummary());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        boolean isAvailable=false;
        if(networkInfo!=null && networkInfo.isConnected()){
            isAvailable=true;
        }
        return isAvailable;
    }

    private void alertUserAboutError()
    {
    AlertDialogFragment dialog=new AlertDialogFragment();
        dialog.show(getFragmentManager(),"error data entry ");
    }

    @OnClick(R.id.daily_button)
    public void startDailyActivity(View view){
        Intent intent=new Intent(this,DailyForecastActivity.class);
        intent.putExtra(DAILY_FORECAST,mForecast.getDailyForecast());
        intent.putExtra(PLACE_NAME,timezone);
        startActivity(intent);
    }

    @OnClick(R.id.hourly_button)
    public void startHourlyACtivity(View view){
        Intent intent=new Intent(this,HourlyForecastActivity.class);
        intent.putExtra(HOURLY_FORECAST,mForecast.getHourlyForecast());
        startActivity(intent);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        requestLocationUpdates();
    }
    private void requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        getForecast(location.getLatitude(),location.getLongitude());
        location2=new LatLng(location.getLatitude(),location.getLongitude());
    }

    @Override
    protected void onStop() {
        super.onStop();

    if(mGoogleApiClient.isConnected()){
        mGoogleApiClient.disconnect();
    }
    }

}
