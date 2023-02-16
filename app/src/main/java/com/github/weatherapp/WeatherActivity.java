package com.github.weatherapp;

import static com.github.weatherapp.MainActivity.API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.github.weatherapp.data.Weather;
import com.github.weatherapp.data.WeatherRes;
import com.github.weatherapp.utils.APIRestService;
import com.github.weatherapp.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Retrofit;

public class WeatherActivity extends AppCompatActivity {

    TextView tvExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //Double latitude = Double.valueOf(getIntent().getStringExtra("latitude"));
        //Double longitude = Double.valueOf(getIntent().getStringExtra("longitude"));

        Double latitude = 40.5;
        Double longitude = -3.7;

        //tvExample = findViewById(R.id.tvExample);

        //tvExample.setText(latitude + " " + longitude);
        getWeather(latitude, longitude);

    }

    private void getWeather(Double latitude, Double longitude) {
        // Call API
        // Get data
        // Set data to Weather class
        // Set data to WeatherRes class

        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<WeatherRes> call = apiRestService.getWeather(API_KEY, 40.5, -3.7);

        call.enqueue(new retrofit2.Callback<WeatherRes>() {
            @Override
            public void onResponse(Call<WeatherRes> call, retrofit2.Response<WeatherRes> response) {
                if (response.isSuccessful()) {
                    WeatherRes weatherRes = response.body();
                    Weather weather = weatherRes.getCurrently();
                    System.out.println(weather.getSummary());
                }
            }

            @Override
            public void onFailure(Call<WeatherRes> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });


    }

}