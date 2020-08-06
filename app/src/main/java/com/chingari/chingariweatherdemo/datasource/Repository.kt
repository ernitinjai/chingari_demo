package com.chingari.chingariweatherdemo.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataBase
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Repository {

    companion object {

        var weatherDataBase: WeatherDataBase? = null

        var getWeatherData: LiveData<List<WeatherModel>>? = null

        fun initializeDB(context: Context) : WeatherDataBase {
            return WeatherDataBase.invoke(context)
        }

        fun insertWeatherData(context: Context, temperature: String, humidity: String, windspeed:String) {

            weatherDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val weather = WeatherModel(temperature, humidity,windspeed)
                weatherDataBase!!.weatherDao().insertAll(weather)
            }

        }

        fun getWeatherData(context: Context) : LiveData<List<WeatherModel>>? {

            weatherDataBase = initializeDB(context)

            getWeatherData = weatherDataBase!!.weatherDao().getAll()

            return getWeatherData as LiveData<List<WeatherModel>>
        }

    }
}