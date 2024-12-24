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
    @GET()
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeather

    @GET()
    suspend fun getForecastWeather(@Url endUrl: String): ForecastWeather
}

object WeatherAPi {
    val retrofitService: WeatherApiService by lazy{
        retrofit.create(WeatherApiService::class.java)
    }

}