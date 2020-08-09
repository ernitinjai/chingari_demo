package com.chingari.chingariweatherdemo.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataModel
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataBase
import com.chingari.chingariweatherdemo.util.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

open class Repository() {
    //TODO : We can move network request here


        var weatherDataBase: WeatherDataBase? = null

        fun initializeDB(context: Context) : WeatherDataBase {
            return WeatherDataBase.invoke(context)
        }

        fun insertWeatherData(context: Context, temperature: String, humidity: String, windspeed:String) {

            weatherDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val dateCreated= Date()
                var dateutil = DateUtils()
                var dateString = dateutil.formatDate(dateCreated)
                val weather = WeatherDataModel(temperature, humidity,windspeed,dateString)
                weatherDataBase!!.weatherDao().insert(weather)
            }

        }

        fun getSavedWeatherData(context: Context) : LiveData<List<WeatherDataModel>> {
             weatherDataBase = initializeDB(context)
             return weatherDataBase!!.weatherDao().getAll()

        }

       fun getLatestWeatherData(context: Context) : WeatherDataModel {
             weatherDataBase = initializeDB(context)
             var weatherDataModel = weatherDataBase!!.weatherDao().getLatest()
             return weatherDataModel

        }
}