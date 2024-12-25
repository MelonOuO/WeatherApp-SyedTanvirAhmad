package com.example.weatherapp.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDate(dt: Int, pattern: String = "dd/MM/yyyy"): String{
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(dt.toLong() * 1000))
}
fun getIconUrl(icon: String ): String{
    Log.d("getIconUrl","https://openweathermap.org/img/wn/$icon@2x.png" )
    return "https://openweathermap.org/img/wn/$icon@2x.png"
}