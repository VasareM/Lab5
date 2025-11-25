package com.example.lab5;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class WeatherService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(() -> {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL(MainActivity.URL).openConnection();
                con.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) json.append(line);
                reader.close();

                // send broadcast
                Intent broadcast = new Intent("weather_data_loaded");
                broadcast.putExtra("json", json.toString());
                broadcast.putExtra("place", "Vilnius");
                sendBroadcast(broadcast);

            } catch (Exception e) {
                Log.e("WeatherService", "Error: " + e.getMessage());
            }
            stopSelf();
        }).start();

        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) { return null; }
}
