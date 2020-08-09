package com.chingari.chingariweatherdemo.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM weather_items ORDER BY date DESC")
    fun getAll(): LiveData<List<WeatherDataModel>>

    @Insert
    fun insert(vararg wetherData: WeatherDataModel)

    @Query("SELECT * FROM weather_items ORDER BY Id DESC LIMIT 1")
    fun getLatest():WeatherDataModel

}