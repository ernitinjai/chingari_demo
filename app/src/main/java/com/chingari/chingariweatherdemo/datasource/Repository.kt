package com.chingari.chingariweatherdemo.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class Repository {

    companion object {

        var weatherDataBase: WeatherDataBase? = null

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

        fun getWeatherData(context: Context) : LiveData<List<WeatherModel>> {

            weatherDataBase = initializeDB(context)
            return weatherDataBase!!.weatherDao().getAll()
        }

    }
}