package com.chingari.chingariweatherdemo.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM weather_items")
    fun getAll(): LiveData<List<GetWeatherLocalRepository>>

    @Insert
    fun insertAll(vararg wetherData: GetWeatherLocalRepository)

}