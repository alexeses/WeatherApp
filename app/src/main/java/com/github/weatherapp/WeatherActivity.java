package com.github.weatherapp;

import static com.github.weatherapp.MainActivity.API_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.weatherapp.data.Weather;
import com.github.weatherapp.data.WeatherRes;
import com.github.weatherapp.utils.APIRestService;
import com.github.weatherapp.utils.RetrofitClient;

import java.util.Date;

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

        //Double latitude = Double.valueOf(getIntent().getStringExtra("latitude"));
        //Double longitude = Double.valueOf(getIntent().getStringExtra("longitude"));

        Double latitude = 40.5;
        Double longitude = -3.7;

        //tvExample = findViewById(R.id.tvExample);

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
                    tvTime.setText(String.format("%s", millisecondsToDate(weather.getTime())));
                    tvTemperature.setText(String.format("%sº", farToCel(weather.getTemperature())));
                    tvHumidity.setText(weather.getHumidity().toString());
                    tvWind.setText(weather.getWindSpeed().toString());
                    tvSummary.setText(weather.getSummary());


                }
            }

            @Override
            public void onFailure(Call<WeatherRes> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });


    }

    private String farToCel(Double temperature) {
        return String.format("%.2f", (temperature - 32) * 5 / 9);
    }

    private String millisecondsToDate(Integer time) {
        Date date = new Date(time * 1000L);

        return date.getHours() + ":" + date.getMinutes() + "h";
    }

}