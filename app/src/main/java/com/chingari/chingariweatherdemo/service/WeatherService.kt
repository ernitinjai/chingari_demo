package com.chingari.chingariweatherdemo.service

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.IBinder
import com.chingari.chingariweatherdemo.ui.WeatherWidgetActivity

class WeatherService : Service() {

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    val appWidgetManager = AppWidgetManager.getInstance(this)
    val allWidgetIds = intent?.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)

    if (allWidgetIds != null) {
      for (appWidgetId in allWidgetIds) {
        WeatherWidgetActivity.updateAppWidget(this, appWidgetManager, appWidgetId)
      }
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onBind(intent: Intent): IBinder? {
    return null
  }
}
