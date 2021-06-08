package com.logan.twitterweather.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindDTO(
    @field:Json(name = "speed")val speed: Double?,
    @field:Json(name = "deg")val deg: Double?
)
