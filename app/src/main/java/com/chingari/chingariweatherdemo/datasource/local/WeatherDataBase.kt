package com.chingari.chingariweatherdemo.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WeatherDataModel::class), version = 1)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDataDao

    companion object {
        @Volatile private var instance: WeatherDataBase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            WeatherDataBase::class.java, "weather.db")
            .build()
    }
}