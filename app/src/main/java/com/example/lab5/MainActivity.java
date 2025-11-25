package com.example.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataLoader.Callback {

    public static final String URL = "https://api.meteo.lt/v1/places/vilnius/forecasts/long-term";

    private EditText edFilter;
    private TextView tvStatus;
    private ListView lvItems;
    private Switch swUseAsync;

    private List<ForecastItem> allItems = new ArrayList<>();
    private ForecastAdapter adapter;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String json = intent.getStringExtra("json");
            String place = intent.getStringExtra("place");
            List<ForecastItem> items = new Parser().parseJSON(json);
            onSuccess(items, place);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edFilter = findViewById(R.id.edFilter);
        tvStatus = findViewById(R.id.tvStatus);
        swUseAsync = findViewById(R.id.swUseAsync);
        lvItems = findViewById(R.id.lvItems);

        lvItems.setScrollbarFadingEnabled(false);

        adapter = new ForecastAdapter(this, allItems);
        lvItems.setAdapter(adapter);

        edFilter.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { onFilterTextChanged(s.toString()); }
            @Override public void afterTextChanged(Editable s) {}
        });

        swUseAsync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            int color = getResources().getColor(R.color.strong_color);
            swUseAsync.getThumbDrawable().setTint(isChecked ? color : Color.WHITE);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver,
                new IntentFilter("weather_data_loaded"),
                Context.RECEIVER_NOT_EXPORTED);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void onBtnGetDataClick(View v) {
        tvStatus.setText("Loading...");
        if (swUseAsync.isChecked()) {
            new DataLoader(this).execute(URL);
        } else {
            startService(new Intent(this, WeatherService.class));
        }
    }

    @Override
    public void onSuccess(List<ForecastItem> items) { onSuccess(items, "Vilnius"); }

    public void onSuccess(List<ForecastItem> items, String place) {
        allItems.clear();
        for (ForecastItem item : items) {
            if (!item.getConditionCode().toLowerCase().contains("cloudy")) {
                allItems.add(item);
            }
        }
        adapter.notifyDataSetChanged();
        tvStatus.setText("Loaded " + allItems.size() + " items for " + place);
    }

    @Override
    public void onError(String msg) {
        tvStatus.setText("Error: " + msg);
    }

    private void onFilterTextChanged(String query) {
        Log.d("MainActivity", "onFilterTextChanged called with query: " + query);
        List<ForecastItem> filtered = new ArrayList<>();
        for (ForecastItem item : allItems) {
            if (query.isEmpty() || item.getForecastTimeUtc().toLowerCase().contains(query.toLowerCase())) {
                filtered.add(item);
            }
        }
        adapter.clear();
        adapter.addAll(filtered);
        adapter.notifyDataSetChanged();
    }


}
