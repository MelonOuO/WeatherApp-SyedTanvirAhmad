package com.example.weatherapp.data

import com.example.weatherapp.network.WeatherAPi

interface WeatherRepository{
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): ForecastWeather

}

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return WeatherAPi.retrofitService.getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForecastWeather {
        return WeatherAPi.retrofitService.getForecastWeather(endUrl)
    }

}