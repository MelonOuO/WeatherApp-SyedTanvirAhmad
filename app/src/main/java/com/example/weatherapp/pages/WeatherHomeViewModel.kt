package com.example.weatherapp.pages

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather
import com.example.weatherapp.data.WeatherRepository
import com.example.weatherapp.data.WeatherRepositoryImpl
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WeatherHomeViewModel: ViewModel() {
    private val TAG = "WeatherHomeViewModel"
    private val weatherRepository: WeatherRepository = WeatherRepositoryImpl()
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)

    private val exceptionHandler = CoroutineExceptionHandler{ _, _ ->
        uiState = WeatherHomeUiState.Error
    }

    fun getWeatherData(){
        viewModelScope.launch(exceptionHandler) {
            uiState = try{
                val currentWeather = async { getCurrentData() }.await()
                val forecastWeather = async { getForecastData() }.await()
                Log.d(TAG, "currentData: ${currentWeather.main!!.temp}")
                Log.d(TAG, "forecastData: ${forecastWeather.list!!.size}")
                WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))
            }catch(e: Exception){
                WeatherHomeUiState.Error

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
