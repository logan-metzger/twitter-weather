package com.logan.twitterweather.repositories

import com.logan.twitterweather.models.WeatherResponseDTO
import com.logan.twitterweather.remote.WeatherManager
import retrofit2.Response

object WeatherRepository {
    suspend fun getCurrentWeather(): Response<WeatherResponseDTO> {
        return WeatherManager().getCurrentWeather()
    }

    suspend fun getFutureWeather(day: String): Response<WeatherResponseDTO> {
        return WeatherManager().getFutureWeather(day)
    }
}