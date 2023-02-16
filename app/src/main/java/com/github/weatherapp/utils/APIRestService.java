package com.github.weatherapp.utils;

import com.github.weatherapp.data.WeatherRes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRestService {
    public static final String BASE_URL = "https://api.darksky.net/forecast/";

    @GET("{key}/{latitude},{longitude}?exclude=minutely,hourly,daily,alerts,flags&lang=es")
    Call<WeatherRes> getWeather(@Path("key") String key, @Path("latitude") Double latitude, @Path("longitude") Double longitude);
}
