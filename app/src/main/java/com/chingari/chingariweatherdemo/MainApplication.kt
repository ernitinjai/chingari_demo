package com.chingari.chingariweatherdemo

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo



class MainApplication : Application(){


    companion object {
        lateinit var instance: MainApplication
            private set
    }

    override fun onCreate(){
        super.onCreate()
        instance = this
    }



    fun hasNetwork(): Boolean {
        return isNetworkAvailable()
    }

    private fun isNetworkAvailable(): Boolean {
        var isConnected = false
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork != null && activeNetwork.isConnected && connectivityManager.activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI)
            isConnected = true
        return isConnected
    }

}