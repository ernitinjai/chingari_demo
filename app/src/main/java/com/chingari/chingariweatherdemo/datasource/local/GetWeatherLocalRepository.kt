package com.chingari.chingariweatherdemo.datasource.local

import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chingari.chingariweatherdemo.model.WeatherResponse

@Entity(tableName = "weather_items")
data class GetWeatherLocalRepository(
        @PrimaryKey(autoGenerate = true)
        var id: Int,

        @ColumnInfo(name = "temprature") var temprature: String,
        @ColumnInfo(name = "humidity") var humidity: String,
        @ColumnInfo(name = "windspeed") var windspeed: String
    )

