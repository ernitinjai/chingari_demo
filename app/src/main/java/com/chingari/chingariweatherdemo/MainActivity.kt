package com.chingari.chingariweatherdemo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.chingari.chingariweatherdemo.util.Constants
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chingari.chingariweatherdemo.model.WeatherResponse
import kotlinx.android.synthetic.main.content_weatherdata.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    val forcast = ArrayList<WeatherResponse>()
    val adapter = WeatherDataAdapter(forcast)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupPermissions()
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
    }


    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }else{
            getLocation()
        }
    }

    private fun getLocation(){
        locationInfo.startLocationFinder()
    }

    private fun showWeather(location: Location) {
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        //Lets call viewmodel to get the new weather data.
        viewModel.getCurrentWeatherData(location)

        val weatherObserver = Observer<WeatherResponse> { weatherResponse ->
            forcast.add(weatherResponse)
            adapter.updateList(forcast)
        }

        //register observer to viewmodel, its indepenedent from UI thread
        viewModel._currentWeather.observe(this, weatherObserver)

    }


    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            Constants.LOCATION_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    //Permission not granted, lets provide details to user why we need it.
                    showAssistMessage()
                }else{
                    getLocation()
                }
                //TODO : Handle Permission Denied case
            }
        }
    }

    fun showAssistMessage() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.location_ask_title)
        builder.setMessage(R.string.location_ask_message)
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton(getString(R.string.location_ask_button_label)){dialogInterface, which ->
            setupPermissions()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    var locationInfo: LocationInfo
        get(){
            var locationInfo =
                LocationInfo(this, object : LocationInfo.OnLocationDataReceived {
                    override fun onSuccess(location: Location) {
                        showWeather(location)
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