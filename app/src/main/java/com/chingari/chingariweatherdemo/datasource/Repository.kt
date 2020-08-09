package com.chingari.chingariweatherdemo.datasource

import android.content.Context
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataBase
import com.chingari.chingariweatherdemo.util.DateUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class Repository(val networkRepository: NetworkRequest = NetworkRequest()) {


    companion object {

        var weatherDataBase: WeatherDataBase? = null

        fun initializeDB(context: Context) : WeatherDataBase {
            return WeatherDataBase.invoke(context)
        }

        fun insertWeatherData(context: Context, temperature: String, humidity: String, windspeed:String) {

            weatherDataBase = initializeDB(context)

            CoroutineScope(IO).launch {
                val dateCreated: Date = Date()
                var dateutil:DateUtils = DateUtils()
                var dateString: String = dateutil.formatDate(dateCreated)
                val weather = WeatherModel(temperature, humidity,windspeed,dateString)
                weatherDataBase!!.weatherDao().insertAll(weather)
            }

        }

        fun getSavedWeatherData(context: Context) : LiveData<List<WeatherModel>> {
             weatherDataBase = initializeDB(context)
             return weatherDataBase!!.weatherDao().getAll()

        }


    }
}