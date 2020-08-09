package com.chingari.chingariweatherdemo.datasource.local

import android.text.TextUtils
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.chingari.chingariweatherdemo.model.WeatherResponse
import java.util.*

@Entity(tableName = "weather_items")
data class WeatherDataModel(
        @ColumnInfo(name = "temperature")
        var temperature: String,
        @ColumnInfo(name = "humidity")
        var humidity: String,
        @ColumnInfo(name = "windspeed")
        var windspeed: String,
        @ColumnInfo(name = "date")
        var dateCreated: String
    ){
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int?=null
}

