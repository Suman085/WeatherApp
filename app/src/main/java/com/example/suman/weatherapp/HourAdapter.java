package com.example.suman.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suman.weatherapp.model.Hour;

/**
 * Created by Suman on 1/11/2017.
 */

public class HourAdapter extends BaseAdapter{
    private Context mContext;
    private Hour[] mHours;

    public HourAdapter(Context context,Hour[] hours){
        mContext=context;
        mHours=hours;
    }

            @Override
            public int getCount() {
                return mHours.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position)
            {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if(convertView==null){
                    convertView= LayoutInflater.from(mContext).inflate(R.layout.hourly_list_item,null);
                    holder=new ViewHolder();
                    holder.iconImageView=(ImageView)convertView.findViewById(R.id.iconImageView);
                    holder.hourLabel=(TextView)convertView.findViewById(R.id.hourLabel);
                    holder.temperatureLabel=(TextView)convertView.findViewById(R.id.temperatureLabel);
                    holder.summaryLabel=(TextView)convertView.findViewById(R.id.summaryLabel);
                    convertView.setTag(holder);
                }
                else
                {
                    holder=(ViewHolder)convertView.getTag();
                }
                Hour hour=mHours[position];
                holder.iconImageView.setImageResource(hour.getIconId());
                holder.summaryLabel.setText(hour.getSummary());
                holder.hourLabel.setText(hour.getHour());
                holder.temperatureLabel.setText(hour.getTemperature()+"");

                return convertView;
            }

        private static class ViewHolder
        {
            ImageView iconImageView;
            TextView summaryLabel;
            TextView temperatureLabel;
            TextView hourLabel;
        }
        }



