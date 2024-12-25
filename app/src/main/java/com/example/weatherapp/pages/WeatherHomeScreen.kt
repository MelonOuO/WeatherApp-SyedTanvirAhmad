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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import com.example.weatherapp.data.ForecastWeather
import com.example.weatherapp.utils.degree
import com.example.weatherapp.utils.getFormattedDate
import com.example.weatherapp.utils.getIconUrl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherHomeScreen(
    uiState: WeatherHomeUiState,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
        ) {
            Surface(
                color = Color.Transparent,
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .wrapContentSize()
            ) {
                when (uiState) {
                    is WeatherHomeUiState.Error -> Text(
                        text = "Fail to fetch data",
                        style = MaterialTheme.typography.displaySmall
                    )

                    is WeatherHomeUiState.Loading -> Text(
                        text = "Loading",
                        style = MaterialTheme.typography.displaySmall
                    )

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
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        CurrentWeatherSection(
            weather.currentWeather,
            modifier = Modifier.weight(1f))
        ForecastWeather(forecastItems = weather.forecastWeather.list!!)

    }

}

@Composable
fun CurrentWeatherSection(
    currentWeather: CurrentWeather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
        //weather description
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(currentWeather.weather?.get(0)!!.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                onSuccess = { Log.d("CurrentWeatherSection", "Success") },
                onLoading = { Log.d("CurrentWeatherSection", "Loading") },
                onError = {
                    Log.d(
                        "CurrentWeatherSection",
                        "Error ${it.result.throwable.message}"
                    )
                },
            )
            Text(
                text = currentWeather.weather[0]!!.description!!,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            //other weather info
            Column(

            ) {
                Text(
                    text = "Humidity ${currentWeather.main?.humidity} %",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Pressure ${currentWeather.main?.pressure} hPa",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Visibility ${currentWeather.visibility?.div(1000)} km",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.width(10.dp))

            //divider
            Surface(
                modifier = Modifier
                    .width(2.dp)
                    .height(100.dp)
            ) { }
            Spacer(modifier = Modifier.width(10.dp))

            //sunrise sunset
            Column(
            ) {
                Text(
                    text = "Sunrise ${
                        getFormattedDate(
                            currentWeather.sys?.sunrise!!,
                            pattern = "HH:mm"
                        )
                    }",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Sunset ${
                        getFormattedDate(
                            currentWeather.sys.sunset!!,
                            pattern = "HH:mm"
                        )
                    }",
                    style = MaterialTheme.typography.titleMedium
                )
            }


        }

    }
}

@Composable
fun ForecastWeather(
    forecastItems: List<ForecastWeather.ForecastItem?>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(forecastItems.size) { index ->
            ForecastWeatherItem(forecastItems[index]!!)

        }

    }

}

@Composable
fun ForecastWeatherItem(
    item: ForecastWeather.ForecastItem,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Black.copy(alpha = 0.5f)),
        modifier = modifier
    ) {
        Column(
            modifier = modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 顯示星期幾
            Text(
                text = getFormattedDate(item.dt!!, pattern = "EEE"),
                style = MaterialTheme.typography.titleMedium
            )
            // 顯示時間
            Text(
                text = getFormattedDate(item.dt!!, pattern = "HH:mm"),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(10.dp))
            //顯示天氣icon
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(getIconUrl(item.weather?.get(0)?.icon!!))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(vertical = 4.dp),
                onSuccess = { Log.d("ForecastWeatherItem", "Success") },
                onLoading = { Log.d("ForecastWeatherItem", "Loading") },
                onError = { Log.d("ForecastWeatherItem",
                    "Error ${it.result.throwable.message}") },
            )

            Spacer(modifier = Modifier.height(10.dp))

            // 顯示溫度
            Text(
                text = "${item.main?.temp?.toInt()}$degree",
                style = MaterialTheme.typography.titleMedium
            )



        }
    }


}