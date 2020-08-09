package com.chingari.chingariweatherdemo.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.chingari.chingariweatherdemo.R
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherModel
import kotlinx.android.synthetic.main.content_weatherdata.*
import com.chingari.chingariweatherdemo.databinding.ActivityMainBinding
import com.chingari.chingariweatherdemo.viewmodel.WeatherViewModel

class WeatherActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val adapter = WeatherDataAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        binding.vm = ViewModelProviders.of(this).get(WeatherViewModel::class.java)
        setupPermissions()

    }


    private fun setUpRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter

        binding.vm!!.weatherData?.observe(this, object : Observer<List<WeatherModel>> {
            override fun onChanged(t: List<WeatherModel>) {
                adapter.updateList(t)
            }
        })
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }else{
            setUpRecyclerView()
        }
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
                    setUpRecyclerView()
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
        builder.setPositiveButton(getString(R.string.location_ask_button_label)){ dialogInterface, which ->
            setupPermissions()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}