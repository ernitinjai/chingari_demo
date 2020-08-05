package com.chingari.chingariweatherdemo.worker

import android.content.Context
import android.location.Location
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.demo.sncr_demo.core.location.LocationInfo
import com.demo.sncr_demo.datasource.remote.GetWeatherRequest
import com.demo.sncr_demo.domain.model.WeatherResponse
import androidx.work.ListenableWorker
import com.google.common.util.concurrent.ListenableFuture
import io.reactivex.Single
import androidx.concurrent.futures.ResolvableFuture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import com.google.android.gms.location.LocationServices

import android.util.Log
import com.demo.sncr_demo.core.presentation.ResourceState
import com.demo.sncr_demo.datasource.local.GetWeatherLocalRepository
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by Nitin Choudhary on 2020-01-13.
 * Batooni pvt ltd
 * ernitinjai@gmail.com
 */

class GetWeatherDataPeriodicWorker(ctx: Context, params: WorkerParameters) : ListenableWorker(ctx, params) {


    private val future : ResolvableFuture<Result> = ResolvableFuture.create()


    override fun startWork(): ListenableFuture<Result> {

        CoroutineScope(Default).launch {
            locationInfo.startLocationFinder()
        }
        return future

    }

    // TODO : we have to add this in chain work
    fun getCurrentWeather(location: Location) {
        val netWorkApi = GetWeatherRequest()

        netWorkApi.getWeather(object : GetWeatherRequest.OnWeatherDataReceived {
            override fun onSuccess(data: WeatherResponse) {
                // Return the output for the temp file
                //TODO: Move it to util
                val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
                data.time  = sdf.format(Date())
                GetWeatherLocalRepository.getWeatherResponse = data
                // If there were no errors, return SUCCESS
                future.set(Result.success())

            }

            //TODO : We havent handle it yet
            override fun onFailure() {
                    future.set(Result.success(Data.EMPTY))
            }
        }, location)
    }

    var locationInfo: LocationInfo
        get(){
            var locationInfo =
                LocationInfo(applicationContext, object : LocationInfo.OnLocationDataReceived {
                    override fun onSuccess(location: Location) {
                        getCurrentWeather(location)
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