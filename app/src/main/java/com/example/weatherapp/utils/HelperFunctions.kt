package com.example.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getFormattedDate(dt: Int, pattern: String = "dd/MM/yyyy"): String{
    return SimpleDateFormat(pattern, Locale.getDefault()).format(Date(dt.toLong() * 1000))
}