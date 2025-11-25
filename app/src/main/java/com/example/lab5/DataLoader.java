package com.example.lab5;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DataLoader extends AsyncTask<String, Void, List<ForecastItem>> {

    public interface Callback {
        void onSuccess(List<ForecastItem> items);
        void onError(String msg);
    }

    private Callback callback;

    public DataLoader(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected List<ForecastItem> doInBackground(String... urls) {
        StringBuilder response = new StringBuilder();
        try {
            HttpURLConnection con = (HttpURLConnection) new URL(urls[0]).openConnection();
            con.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) response.append(line);
            reader.close();
        } catch (Exception e) {
            Log.e("DataLoader", "Network error: " + e.getMessage());
            return null;
        }
        return new Parser().parseJSON(response.toString());
    }

    @Override
    protected void onPostExecute(List<ForecastItem> result) {
        if (result == null) callback.onError("Failed to load weather data");
        else callback.onSuccess(result);
    }
}
