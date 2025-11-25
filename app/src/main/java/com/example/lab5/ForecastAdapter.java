package com.example.lab5;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
        ForecastItem item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.forecast_item, parent, false);
        }

        TextView tvTime = convertView.findViewById(R.id.tvTime);
        TextView tvDetails = convertView.findViewById(R.id.tvDetails);

        tvTime.setText(item.getForecastTimeUtc());

        String details = item.getAirTemperature() + "°C | feels: " + item.getFeelsLikeTemperature() +
                "°C | wind " + item.getWindSpeed() + " m/s";
        SpannableString spannable = new SpannableString(details);

        int end = String.valueOf(item.getAirTemperature()).length();
        if (item.getAirTemperature() < 0) {
            spannable.setSpan(new ForegroundColorSpan(Color.BLUE), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else if (item.getAirTemperature() > 0) {
            spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        tvDetails.setText(spannable);


        return convertView;
    }

}
