package com.logan.twitterweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDTO(
    @field:Json(name = "temp")val temp: Double = 0.00,
    @field:Json(name = "pressure")val pressure: Double?,
    @field:Json(name = "humidity")val humidity: Double?,
)
