package com.chingari.chingariweatherdemo.datasource

import android.location.Location
import com.chingari.chingariweatherdemo.BaseNetworkRequest
import com.chingari.chingariweatherdemo.model.WeatherResponse
import com.chingari.chingariweatherdemo.util.Constants
import retrofit2.Call
import retrofit2.Response



class GetWeatherRequest : BaseNetworkRequest<GetWetherAPI>(
    GetWetherAPI::class.java) {

    fun getWeather(onWeatherDataReceived: OnWeatherDataReceived,location: Location) {
        makeRequest().getData(location.latitude.toString(),location.longitude.toString(), Constants.OPEN_WEATHER_KEY).enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                onWeatherDataReceived.onSuccess((response.body() as WeatherResponse))
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                onWeatherDataReceived.onFailure()
            }
        })
    }

    interface OnWeatherDataReceived {
        fun onSuccess(data: WeatherResponse)
        fun onFailure()
    }

}