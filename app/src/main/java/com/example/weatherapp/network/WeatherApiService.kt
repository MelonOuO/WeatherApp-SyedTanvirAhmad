package com.example.weatherapp.network

import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.data.ForecastWeather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url


private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

 private val retrofit = Retrofit.Builder()
     .baseUrl(BASE_URL)
     .addConverterFactory(GsonConverterFactory.create())
     .build()


interface WeatherApiService{
    @GET("weather?lat=24.79594349295699&lon=120.98266020739521&appid=935ec2f1d3ec6ff0a7f93c9e1f22960e&units=metric")
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET("forecast?lat=24.79594349295699&lon=120.98266020739521&appid=935ec2f1d3ec6ff0a7f93c9e1f22960e&units=metric")
    suspend fun getForecastWeather(@Url endUrl: String): ForecastWeather
}

object WeatherAPi {
    val retrofitService: WeatherApiService by lazy{
        retrofit.create(WeatherApiService::class.java)
    }

}