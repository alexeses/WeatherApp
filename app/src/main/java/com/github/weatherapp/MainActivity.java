package com.github.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "11ce4328111023379e0fdc9d28c24a02";
    EditText etLatitude;
    EditText etLongitude;
    Button btnGetWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLatitude = findViewById(R.id.etLatidude);
        etLongitude = findViewById(R.id.etLongitude);
        btnGetWeather = findViewById(R.id.btnGetWeather);

        btnGetWeather.setOnClickListener(v -> {
            Double latitude = Double.valueOf(etLatitude.getText().toString());
            Double longitude = Double.valueOf(etLongitude.getText().toString());

            Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
            intent.putExtra("latitude", latitude.toString());
            intent.putExtra("longitude", longitude.toString());
            startActivity(intent);

        });

    }
}