package com.chingari.chingariweatherdemo.viewmodel

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.LocationInfo
import com.chingari.chingariweatherdemo.datasource.NetworkRequest
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import com.chingari.chingariweatherdemo.model.WeatherResponse

class WeatherViewModel @JvmOverloads constructor(app: Application, val networkRequest: NetworkRequest = NetworkRequest()) : ObservableViewModel(app), NetworkRequest.OnWeatherDataReceived {

    var networkDataListener: NetworkRequest.OnWeatherDataReceived? = null

    init {
        networkDataListener = this
    }


    override fun onSuccess(data: WeatherResponse) {
        Repository.insertWeatherData(
            getApplication(),
            data.main.temp.toString(),
            data.main.humidity.toString(),
            data.main.pressure.toString()
        )
    }

    override fun onFailure() {

    }

    fun startLocationFinder() {
        locationInfo.startLocationFinder()
    }

    fun getSavedWeatherData(): LiveData<List<WeatherModel>> {
        startLocationFinder()
        return Repository.getSavedWeatherData(getApplication())
    }



    private fun getCurrentWeatherData(location: Location) {
        networkRequest.getWeather(networkDataListener!!,location)
    }

    var locationInfo: LocationInfo
        get(){
            var locationInfo =
                LocationInfo(getApplication(), object : LocationInfo.OnLocationDataReceived {
                    override fun onSuccess(location: Location) {
                        getCurrentWeatherData(location)
                    }
                    //TODO : We havent handle it yet
                    override fun onFailure() {
                        Log.d("","")
                    }

                })

            return locationInfo
        }
        set(value){
            locationInfo = value
        }

}

