package com.logan.twitterweather.Utils.MainActivity

import com.logan.twitterweather.MainActivity

fun MainActivity.convertToFahrenheit(temp: Double): Double {
    return temp * 1.8 + 32
}

