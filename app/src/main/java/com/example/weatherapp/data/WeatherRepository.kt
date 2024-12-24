package com.example.weatherapp.data

interface WeatherRepository{
    suspend fun getCurrentWeather(endUrl: String): CurrentWeather
    suspend fun getForecastWeather(endUrl: String): ForecastWeather

}

class WeatherRepositoryImpl : WeatherRepository {
    override suspend fun getCurrentWeather(endUrl: String): CurrentWeather {
        return getCurrentWeather(endUrl)
    }

    override suspend fun getForecastWeather(endUrl: String): ForecastWeather {
        return getForecastWeather(endUrl)
    }

}