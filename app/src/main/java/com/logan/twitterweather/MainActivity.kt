package com.logan.twitterweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.logan.twitterweather.Utils.MainActivity.convertToFahrenheit
import com.logan.twitterweather.databinding.ActivityMainBinding
import com.logan.twitterweather.ui.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var savedWind: String = null.toString()
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            viewModel.currentWeather.observe(this@MainActivity, {response ->
                tempCelsius.text = response.weather?.temp.toString()
                tempFahrenheit.text =
                    response.weather?.temp?.let { it1 -> convertToFahrenheit(it1).toString() }
                windSpeed.text = response.wind?.speed.toString()

                if (response.clouds?.cloudiness!! > 49) {
                    cloudIcon.setImageResource(R.drawable.ic_baseline_cloud_24)
                }
            })

            getWeatherButton.setOnClickListener {
                viewModel.getFutureWeather(5)
            }

            viewModel.standardDeviation.observe(this@MainActivity, {
                    standardDeviation.text = it.toString()
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("tempCelsius", binding.tempCelsius.text.toString())
        outState.putString("tempFahrenheit", binding.tempFahrenheit.text.toString())
        outState.putString("windSpeed", savedWind)
        outState.putString("standardDeviation", binding.standardDeviation.text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        with(binding) {
            tempCelsius.text = savedInstanceState.getString("tempCelsius")
            tempFahrenheit.text = savedInstanceState.getString("tempFahrenheit")
            savedWind = savedInstanceState.getString("windSpeed").toString()
            standardDeviation.text = savedInstanceState.getString("standardDeviation")
        }
    }
}