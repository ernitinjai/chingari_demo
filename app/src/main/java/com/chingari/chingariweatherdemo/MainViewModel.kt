package com.chingari.chingariweatherdemo

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import com.chingari.chingariweatherdemo.datasource.GetWeatherRequest
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataDao
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import com.chingari.chingariweatherdemo.model.WeatherResponse

class MainViewModel() : ViewModel(), KoinComponent {

    val _currentWeather: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>()
    }



    fun getCurrentWeatherData(location: Location) {
        val netWorkApi = GetWeatherRequest()

        netWorkApi.getWeather(object : GetWeatherRequest.OnWeatherDataReceived {
            override fun onSuccess(data: WeatherResponse) {
                //TODO : Update the room database and adapter subscribe to it to listen for any change and get update the recycler view
                // -> Room data should sort with date
                _currentWeather.postValue(data)
            }

            //TODO : We havent handle it yet
            override fun onFailure() {

            }
        }, location)
    }



}

