package com.chingari.chingariweatherdemo.model


//Open weather API response Data Model , We have just using bare minimum that fit to our requirment.
data class WeatherResponse(

    val main:Main,
    var time:String
    //TODO : we need to consider time, it will useful for shorting.
)


data class Main(
    var temp:Double,
    var feels_like:Double,
    var temp_min:Double,
    var temp_max:Double,
    var pressure:Integer,
    var humidity:Integer
)

