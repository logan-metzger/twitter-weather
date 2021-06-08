package com.logan.twitterweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordDTO(
    @field:Json(name = "lon")val lon: Double?,
    @field:Json(name = "lat")val lat: Double?
)
