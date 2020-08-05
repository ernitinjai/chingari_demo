package com.chingari.chingariweatherdemo.datasource


import com.chingari.chingariweatherdemo.model.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.Query

interface GetWetherAPI {

    @GET("weather")
    fun getData(@Query("lat") lat: String, @Query("lon") lon: String,@Query("appid") appid: String) : Call<WeatherResponse>
}