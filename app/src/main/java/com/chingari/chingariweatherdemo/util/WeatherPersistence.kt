package com.chingari.chingariweatherdemo.util

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class WeatherPersistence(private val context: Context) {

  companion object {
    private const val PREFS_NAME = "com.chingari.chingariweatherdemo.ui.WeatherWidget"
    private const val PREF_PREFIX_KEY = "weather_logger"
  }

  private val strFormatter = SimpleDateFormat("DDMMYYYY", Locale.US)
  
  // Write the prefix to the SharedPreferences object for this widget
  internal fun saveTitlePref(value: Int) {
    val date = Date()
    val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
    prefs.putInt(PREF_PREFIX_KEY + strFormatter.format(date), value)
    prefs.apply()
  }

  // Read the prefix from the SharedPreferences object for this widget.
  // If there is no preference saved, get the default from a resource
  internal fun loadTitlePref(): Int {
    val date = Date()
    val prefs = context.getSharedPreferences(PREFS_NAME, 0)
    return prefs.getInt(PREF_PREFIX_KEY + strFormatter.format(date), 0)
  }


}
