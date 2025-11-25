package com.example.lab5;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ForecastAdapter extends ArrayAdapter<ForecastItem> {

    public ForecastAdapter(Context context, List<ForecastItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ForecastItem item = getItem(position);  // Now returns ForecastItem

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, parent, false);
        }

        TextView tvTime = convertView.findViewById(R.id.tvTime);
        TextView tvDetails = convertView.findViewById(R.id.tvDetails);

        tvTime.setText(item.getForecastTimeUtc());

        String details = item.getAirTemperature() + "°C | feels: " + item.getFeelsLikeTemperature() +
                "°C | wind " + item.getWindSpeed() + " m/s";

        tvDetails.setText(details);

        // Only temperature changes color
        if (item.getAirTemperature() < 0) tvDetails.setTextColor(Color.BLUE);
        else if (item.getAirTemperature() >= 20) tvDetails.setTextColor(Color.RED);
        else tvDetails.setTextColor(Color.BLACK);

        return convertView;
    }

}
