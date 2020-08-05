package com.chingari.chingariweatherdemo

import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import com.chingari.chingariweatherdemo.datasource.GetWeatherRequest
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataDao
import com.chingari.chingariweatherdemo.model.WeatherResponse

class MainViewModel() : ViewModel(), KoinComponent {

    val _currentWeather: MutableLiveData<WeatherResponse> by lazy {
        MutableLiveData<WeatherResponse>()
    }

    fun getCurrentWeatherData(location: Location) {
        val netWorkApi = GetWeatherRequest()

        netWorkApi.getWeather(object : GetWeatherRequest.OnWeatherDataReceived {
            override fun onSuccess(data: WeatherResponse) {
                _currentWeather.postValue(data)
            }

            //TODO : We havent handle it yet
            override fun onFailure() {

            }
        }, location)
    }



}

