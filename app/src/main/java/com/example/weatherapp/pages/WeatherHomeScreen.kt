package com.example.weatherapp.pages

import android.util.Log
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.weatherapp.R
import com.example.weatherapp.WeatherApp
import com.example.weatherapp.customuis.AppBackground
import com.example.weatherapp.data.CurrentWeather
import com.example.weatherapp.utils.degree
import com.example.weatherapp.utils.getFormattedDate
import com.example.weatherapp.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize()
    ){
        AppBackground(photoId = R.drawable.candid_image_natural_textures)
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Weather App", style = MaterialTheme.typography.titleLarge)
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        actionIconContentColor = Color.White
                    )

                )
            },
            containerColor = Color.Transparent
        ){
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                when(uiState){
                    is WeatherHomeUiState.Error -> Text(text = "Fail to fetch data", style = MaterialTheme.typography.displaySmall)
                    is WeatherHomeUiState.Loading -> Text(text = "Loading", style = MaterialTheme.typography.displaySmall)
                    is WeatherHomeUiState.Success -> WeatherSection(weather = uiState.weather)
                }

            }

        }
    }
}

@Composable
fun WeatherSection(
    weather: Weather,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.padding(8.dp)
    ){
        CurrentWeatherSection(weather.currentWeather)


    }

}
@Composable
fun CurrentWeatherSection(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        //country location
        Text(
            text = "${currentWeather.name}, ${currentWeather.sys?.country}",
            style = MaterialTheme.typography.titleMedium
        )
        //date
        Text(
            text = getFormattedDate(dt = currentWeather.dt!!, pattern = "MM/dd, yyyy"),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(20.dp))
        //temp
        Text(
            text = "${currentWeather.main?.temp?.toInt()}$degree",
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.height(20.dp))
        //feels like
        Text(
            text = "feels like ${currentWeather.main?.feelsLike?.toInt()}$degree",
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,

        ){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(currentWeather.weather?.get(0)!!.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                onSuccess = { Log.d("CurrentWeatherSection", "Success" )},
                onLoading = { Log.d("CurrentWeatherSection", "Loading" )},
                onError = { Log.d("CurrentWeatherSection", "Error ${it.result.throwable.message}")},
            )
            Text(
                text = currentWeather.weather[0]!!.description!!,
                style = MaterialTheme.typography.titleMedium
            )

        }

    }
}