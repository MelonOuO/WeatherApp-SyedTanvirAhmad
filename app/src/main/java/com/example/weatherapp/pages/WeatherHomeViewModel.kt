package com.example.weatherapp.pages

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherHomeViewModel: ViewModel() {
    private val TAG = "WeatherHomeViewModel"
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()

    fun getWeatherData(){
        viewModelScope.launch {
            try{
                val currentWeather = async { getCurrentData() }.await()
                val forecastWeather = async { getForecastData() }.await()
                Log.d(TAG, "currentData: ${currentWeather.main!!.temp}")
                Log.d(TAG, "forecastData: ${forecastWeather.list!!.size}")
            }catch(e: Exception){

            }
        }
    }

    private suspend fun getCurrentData(): CurrentWeather{
        val endUrl: String = "weather?lat=24.79594349295699&lon=120.98266020739521&appid=935ec2f1d3ec6ff0a7f93c9e1f22960e&units=metric"
        return weatherRepository.getCurrentWeather(endUrl)
    }

    private suspend fun getForecastData(): ForecastWeather{
        val endUrl: String = "forecast?lat=24.79594349295699&lon=120.98266020739521&appid=935ec2f1d3ec6ff0a7f93c9e1f22960e&units=metric"
        return weatherRepository.getForecastWeather(endUrl)
    }
}
