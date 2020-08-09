package com.chingari.chingariweatherdemo

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import com.chingari.chingariweatherdemo.datasource.Repository


import java.util.Locale


@SuppressLint("MissingPermission")
class LocationInfo(
    private val context: Context,
    onLocationDataReceived: OnLocationDataReceived
) : LocationListener {

    var onLocationDataaReceived:OnLocationDataReceived
    init{
        onLocationDataaReceived = onLocationDataReceived
    }
    val locationManager : LocationManager
    get(){
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager
    }

    fun startLocationFinder(){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
            MIN_TIME_BW_UPDATES,
            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this, Looper.getMainLooper())
    }

    fun stopLocationFinder(){
        locationManager.removeUpdates(this)
    }



    override fun onLocationChanged(location: Location) {
        onLocationDataaReceived.onSuccess(location)
    }

    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }

    companion object {

        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 500
        private val MIN_TIME_BW_UPDATES: Long = 0
    }

    interface OnLocationDataReceived {
        fun onSuccess(location: Location)
        fun onFailure()
    }
}
