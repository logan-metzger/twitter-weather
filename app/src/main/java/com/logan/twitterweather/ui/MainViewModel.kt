package com.logan.twitterweather.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.logan.twitterweather.models.WeatherResponseDTO
import com.logan.twitterweather.repositories.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.pow
import kotlin.math.sqrt

class MainViewModel : ViewModel() {
    private var _standardDeviation: MutableLiveData<Double> = MutableLiveData()
    val standardDeviation: LiveData<Double> get() = _standardDeviation

    private var _currentWeather: MutableLiveData<WeatherResponseDTO> = MutableLiveData()
    val currentWeather: LiveData<WeatherResponseDTO> get() = _currentWeather

    private var _errorMsg: MutableLiveData<String> = MutableLiveData()
    val errorMsg: LiveData<String> get() = _errorMsg

    private var _progressIsVisible: MutableLiveData<Boolean> = MutableLiveData()
    val progressIsVisible: LiveData<Boolean> get() = _progressIsVisible

    init {
        getCurrentWeather()
    }


    private fun getCurrentWeather() {
        _progressIsVisible.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = WeatherRepository.getCurrentWeather()
                if (response.body() != null && response.isSuccessful) {
                    _currentWeather.postValue(response.body())
                    _errorMsg.postValue("")
                    _progressIsVisible.postValue(false)
                } else {
                    _errorMsg.postValue("No weather found")
                    _progressIsVisible.postValue(false)
                }
            } catch (e: Exception) {
                _progressIsVisible.postValue(false)
                _errorMsg.postValue(e.message)
            }
        }
    }

    /**
     * This function makes consecutive API calls to receive the weather for an amount of days
     * Afterwards it adds the value of the temp field to a mutable list which is then used to
     * calculate the standard deviation. I chose to allow for extensibility by making it possible to
     * easily change the amount of calls to the api if there was need to expand the data set for
     * standard deviation calculation in the future
     */
    fun getFutureWeather(amountOfDays: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                (1..amountOfDays).map { dayNumber ->
                    WeatherRepository.getFutureWeather(dayNumber.toString())
                }.filter {
                    it.isSuccessful && it.body() != null
                }.also {
                    if (it.size == amountOfDays) {
                        val weatherList = it.flatMap { response ->
                            mutableListOf<Double>().also {
                                response.body()?.weather?.temp?.let { it1 -> it.add(it1) }
                            }
                        }
                        calculateStandardDeviation(weatherList)
                    } else {
                        _errorMsg.postValue("Something has gone wrong, not enough data to calculate")
                    }
                }

            } catch (e: Exception) {
                _errorMsg.postValue(e.message)
            }
        }
    }

    private fun calculateStandardDeviation(temps: List<Double>) {
        var sum = 0.0
        var standardDeviation = 0.0

        for (num in temps) {
            sum += num
        }

        val mean = sum / 10

        for (num in temps) {
            standardDeviation += (num - mean).pow(2.0)
        }

        _standardDeviation.postValue(sqrt(standardDeviation / 10))
    }
}