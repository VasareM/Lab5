package com.example.lab5;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public List<ForecastItem> parseJSON(String jsonStr) {
        List<ForecastItem> result = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonStr);
            JSONArray arr = root.getJSONArray("forecastTimestamps");
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String time = obj.getString("forecastTimeUtc");
                double temp = obj.getDouble("airTemperature");
                double feels = obj.getDouble("feelsLikeTemperature");
                String condition = obj.getString("conditionCode");
                double wind = obj.getDouble("windSpeed");
                result.add(new ForecastItem(time, temp, feels, condition, wind));
            }
        } catch (Exception e) {
            Log.e("Parser", "Parsing error: " + e.getMessage());
        }
        return result;
    }

}