package com.logan.twitterweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherResponseDTO(
    @field:Json(name = "coord")val coord: CoordDTO?,
    @field:Json(name = "weather")val weather: WeatherDTO?,
    @field:Json(name = "wind")val wind: WindDTO?,
    @field:Json(name = "rain")val rain: RainDTO?,
    @field:Json(name = "clouds")val clouds: CloudsDTO?,
    @field:Json(name = "name")val name: String
)