package com.chingari.chingariweatherdemo.model


data class WetherRequest (

    var lat:Double,
    var lon:Double,
    var appid:String
    //lat=35&lon=139&appid=07205a525c46d8317b4a342c7356b078
)