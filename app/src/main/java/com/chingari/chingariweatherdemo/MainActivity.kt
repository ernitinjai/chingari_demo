package com.chingari.chingariweatherdemo

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.chingari.chingariweatherdemo.util.Constants
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import com.chingari.chingariweatherdemo.model.WeatherResponse
import kotlinx.android.synthetic.main.content_weatherdata.*
import com.chingari.chingariweatherdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val adapter = WeatherDataAdapter()
    var weatherItems: LiveData<List<WeatherModel>>? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupPermissions()
        setUpRecyclerView()
        weatherItems = Repository.getWeatherData(this)
        weatherItems?.observe(this, object : Observer<List<WeatherModel>> {
            override fun onChanged(t: List<WeatherModel>) {
                adapter.updateList(t)
            }
        })
    }

    private fun setUpRecyclerView() {
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

        binding.vm?.getCurrentWeatherData(location)

       /* val weatherObserver = Observer<WeatherResponse> { weatherResponse ->
            Repository.insertWeatherData(
                this,
                weatherResponse.main.temp.toString(),
                weatherResponse.main.humidity.toString(),
                weatherResponse.main.pressure.toString()
            )
        }*/
        //register observer to viewmodel, its indepenedent from UI thread
        //binding.vm?._currentWeather?.observe(this, weatherObserver)

        //Any update in room database allow UI list to refresh

        //TODO : UI should read database , so offline it could render previous hour data



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
                //TODO : Handle Denied case
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

    //TODO: Should'nt be here
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