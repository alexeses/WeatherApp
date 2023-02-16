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


            if (checkValid(latitude, longitude)) {
                etLatitude.setError("Please enter the latitude");
                etLongitude.setError("Please enter the longitude");
            } else {
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

    }

    private boolean checkValid(Double latitude, Double longitude) {
        if (latitude < -90 || latitude > 90) {
            return true;
        } else if (longitude < -180 || longitude > 180) {
            return true;
        } else {
            return false;
        }
    }
}