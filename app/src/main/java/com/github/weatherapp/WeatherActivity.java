package com.github.weatherapp;

import static com.github.weatherapp.MainActivity.API_KEY;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.weatherapp.data.Weather;
import com.github.weatherapp.data.WeatherRes;
import com.github.weatherapp.utils.APIRestService;
import com.github.weatherapp.utils.RetrofitClient;

import java.util.Date;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Retrofit;

public class WeatherActivity extends AppCompatActivity {

    TextView tvZone;
    ImageView ivWeather;
    TextView tvTime;
    TextView tvTemperature;
    TextView tvHumidity;
    TextView tvWind;
    TextView tvHumedity2;
    TextView tvWind2;
    TextView tvSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        TextView tvZone = findViewById(R.id.tvZone);
        ImageView ivWeather = findViewById(R.id.ivWeather);
        TextView tvTime = findViewById(R.id.tvTime);
        TextView tvTemperature = findViewById(R.id.tvTemp);
        TextView tvHumidity = findViewById(R.id.tvHumidity);
        TextView tvWind = findViewById(R.id.tvWind);
        TextView tvHumedity2 = findViewById(R.id.tvHumidity2);
        TextView tvWind2 = findViewById(R.id.tvWind2);
        TextView tvSummary = findViewById(R.id.tvSummary);

        Double latitude = Double.valueOf(getIntent().getStringExtra("latitude"));
        Double longitude = Double.valueOf(getIntent().getStringExtra("longitude"));

        Retrofit retrofit = RetrofitClient.getClient(APIRestService.BASE_URL);
        APIRestService apiRestService = retrofit.create(APIRestService.class);
        Call<WeatherRes> call = apiRestService.getWeather(API_KEY, latitude, longitude);

        call.enqueue(new retrofit2.Callback<WeatherRes>() {
            @Override
            public void onResponse(Call<WeatherRes> call, retrofit2.Response<WeatherRes> response) {
                if (response.isSuccessful()) {
                    WeatherRes weatherRes = response.body();
                    Weather weather = weatherRes.getCurrently();
                    tvZone.setText(weatherRes.getTimezone());
                    tvTime.setText(String.format("%s", millisecondsToDate(weatherRes.getCurrently().getTime())));
                    tvTemperature.setText(String.format("%sº", farToCel(weather.getTemperature())));
                    tvHumedity2.setText(String.format("%s%%", weather.getHumidity().toString()));
                    tvWind2.setText(String.format("%s%%", weather.getPrecipProbability().toString()));
                    tvSummary.setText(weather.getSummary());
                    changeIcon(ivWeather, weather.getIcon());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherRes> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }

    private void changeIcon(ImageView i, String s) {
        if (Objects.equals(s, "clear-day")) {
            i.setImageResource(R.drawable.clear_day);
        } else if (s.equals("clear-night")) {
            i.setImageResource(R.drawable.clear_night);
        } else if (s.equals("rain")) {
            i.setImageResource(R.drawable.rain);
        } else if (s.equals("snow")) {
            i.setImageResource(R.drawable.snow);
        } else if (s.equals("sleet")) {
            i.setImageResource(R.drawable.sleet);
        } else if (s.equals("wind")) {
            i.setImageResource(R.drawable.wind);
        } else if (s.equals("fog")) {
            i.setImageResource(R.drawable.fog);
        } else if (s.equals("cloudy")) {
            i.setImageResource(R.drawable.cloudy);
        } else if (s.equals("partly-cloudy-day")) {
            i.setImageResource(R.drawable.partly_cloudy);
        } else {
            i.setImageResource(R.drawable.clear_day);
        }
        System.out.println("El tiempo es: " + s);
    }


    private String farToCel(Double temperature) {
        return String.valueOf(Math.round((temperature - 32) * 5 / 9));
    }

    private String millisecondsToDate(Integer time) {
        Date date = new Date(time * 1000L);
        return date.getHours() + ":" + date.getMinutes() + "h";
    }
}