package com.chingari.chingariweatherdemo.viewmodel

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import com.chingari.chingariweatherdemo.LocationInfo
import com.chingari.chingariweatherdemo.datasource.NetworkRequest
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataModel
import com.chingari.chingariweatherdemo.model.WeatherResponse

class WeatherViewModel @JvmOverloads constructor(app: Application,val repository: Repository = Repository() ,val networkRequest: NetworkRequest = NetworkRequest()) : ObservableViewModel(app), NetworkRequest.OnWeatherDataReceived {

    var networkDataListener: NetworkRequest.OnWeatherDataReceived? = null
    var weatherData: LiveData<List<WeatherDataModel>>? =null

    init {
        networkDataListener = this
        weatherData = getSavedWeatherData()
        startLocationFinder()
    }


    override fun onSuccess(data: WeatherResponse) {
        Repository().insertWeatherData(
            getApplication(),
            data.main.temp.toString(),
            data.main.humidity.toString(),
            data.main.pressure.toString()
        )
    }

    override fun onFailure() {

    }

    private fun startLocationFinder() {
        locationInfo.startLocationFinder()
    }

    private fun getSavedWeatherData(): LiveData<List<WeatherDataModel>> {
        return Repository().getSavedWeatherData(getApplication())
    }



    private fun getCurrentWeatherData(location: Location) {
        networkRequest.getWeather(networkDataListener!!,location)
    }

    var locationInfo: LocationInfo
        get(){
            var locationInfo =
                LocationInfo(getApplication(), object : LocationInfo.OnLocationDataReceived {
                    override fun onSuccess(location: Location) {
                        locationInfo.stopLocationFinder()
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

