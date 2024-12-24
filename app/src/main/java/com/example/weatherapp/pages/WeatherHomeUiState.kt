package com.example.weatherapp.pages

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather

data class Weather(
    val currentWeather: CurrentWeather,
    val forecastWeather: ForecastWeather
)

sealed interface WeatherHomeUiState{
    data class Success(val weather: Weather): WeatherHomeUiState
    data object Error : WeatherHomeUiState
    data object Loading : WeatherHomeUiState

}

