package com.chingari.chingariweatherdemo

import android.app.Application
import com.chingari.chingariweatherdemo.datasource.Repository
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataBase
import com.chingari.chingariweatherdemo.datasource.local.WeatherDataModel
import com.chingari.chingariweatherdemo.viewmodel.WeatherViewModel
import junit.framework.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.util.*

class WeatherDataViewModelTest {

    lateinit var weatherDataViewModel: WeatherViewModel

    @Mock
    lateinit var mockWeatherRepository: Repository

    @Mock
    lateinit var mockApplication: Application

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockWeatherRepository.initializeDB(mockApplication)
        weatherDataViewModel = WeatherViewModel(mockApplication, mockWeatherRepository)
    }

    @Test
    fun testSaveWeatherData() {
        val weatherData = WeatherDataModel(temperature = "125.0", humidity = "12",windspeed="13",dateCreated = "12")
        verify(mockWeatherRepository, never()).insertWeatherData(mockApplication,temperature = "125.0", humidity = "12",windspeed="13")
        assertNotEquals(mockWeatherRepository.getSavedWeatherData(mockApplication), weatherData.dateCreated)
    }
}