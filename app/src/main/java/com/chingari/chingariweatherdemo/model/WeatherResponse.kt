package com.chingari.chingariweatherdemo.model


//Open weather API response Data Model
data class WeatherResponse(

    val weather: Array<Weather>,
    val pressure: Double,
    val speed: Double,

    val coord:Coord,
    val base:String,
    val wind:Wind,
    val rain:Rain,
    val clouds:Clouds,
    val dt:Integer,
    val timezone:Integer,
    val sys:Sys,
    val name:String,
    val cod:Integer,
    val main:Main,
    var time:String
)


data class Weather(

    var id:Integer,
    var main:String,
    var description:String,
    var icon:String


)

data class Coord(
    val lon:Double,
    val lat:Double
)

data class Wind(
    var speed:Double,
    var deg:Integer,
    var gust:Double
)

data class Rain(
    var h:Double
)

data class Clouds(
    var all:Integer
)

data class Sys(

    var type:Integer,
    var id:Integer,
    var country:String,
    var sunrise:Integer,
    var sunset:Integer,
    var name:String
)

data class Main(
    var temp:Double,
    var feels_like:Double,
    var temp_min:Double,
    var temp_max:Double,
    var pressure:Integer,
    var humidity:Integer
)

