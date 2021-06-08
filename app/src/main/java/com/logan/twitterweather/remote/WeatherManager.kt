package com.logan.twitterweather.remote

import com.logan.twitterweather.models.WeatherResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class WeatherManager {
    private val service: WeatherService
    private val retrofit = RetrofitInstance.providesRetrofitService()

    init {
        service = retrofit.create(WeatherService::class.java)
    }

    suspend fun getCurrentWeather() = service.getCurrentWeather()

    suspend fun getFutureWeather(day: String) = service.getFutureWeather(day)

    interface WeatherService {
        @GET("/current.json")
        suspend fun getCurrentWeather(
        ): Response<WeatherResponseDTO>

        @GET("/future_{day}.json")
        suspend fun getFutureWeather(
            @Path("day") day: String
        ): Response<WeatherResponseDTO>
    }
}