package com.chingari.chingariweatherdemo.model


//Open weather API response Data Model
data class WeatherResponse(

    val main:Main,
    var time:String
)


data class Main(
    var temp:Double,
    var feels_like:Double,
    var temp_min:Double,
    var temp_max:Double,
    var pressure:Integer,
    var humidity:Integer
)

