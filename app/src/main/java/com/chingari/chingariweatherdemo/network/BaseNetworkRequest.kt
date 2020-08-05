package com.chingari.chingariweatherdemo
import com.chingari.chingariweatherdemo.network.RetrofitManager
import com.chingari.chingariweatherdemo.util.Constants
import retrofit2.Retrofit


abstract class BaseNetworkRequest<T>(private val baseUrl: String, private var clazz: Class<T>) {


    //http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139&appid=07205a525c46d8317b4a342c7356b078
    constructor(clazz: Class<T>) : this(Constants.OPEN_WEATHER_URL, clazz)

    protected fun makeRequest(): T {
        return makeRetrofitService(baseUrl).create(this.clazz)
    }

    private fun makeRetrofitService(baseUrl: String): Retrofit {
        return RetrofitManager.getInstance().makeRetrofit(baseUrl)
    }

}