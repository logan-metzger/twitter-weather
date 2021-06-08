package com.logan.twitterweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RainDTO(
    @Json(name = "3h")val threeH: Double?
)