package com.chingari.chingariweatherdemo.ui

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.chingari.chingariweatherdemo.R
import com.chingari.chingariweatherdemo.service.WeatherService
import com.chingari.chingariweatherdemo.util.WeatherPersistence

/**
 * Implementation of App Widget functionality.
 */
class WeatherWidgetActivity : AppWidgetProvider() {

  override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
    val intent = Intent(context.applicationContext, WeatherService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds)
    context.startService(intent)
  }

  override fun onEnabled(context: Context) {
    // Enter relevant functionality for when the first widget is created
  }

  override fun onDisabled(context: Context) {
    // Enter relevant functionality for when the last widget is disabled
  }

  companion object {

    internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                 appWidgetId: Int) {

      val weatherPersistence = WeatherPersistence(context)

      val widgetText = weatherPersistence.loadTitlePref().toString()

      // Construct the RemoteViews object
      val views = RemoteViews(context.packageName, R.layout.weather_logger_widget)
      views.setTextViewText(R.id.appwidget_text, widgetText)

      
      // Instruct the widget manager to update the widget
      appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingIntent(context: Context, value: Int): PendingIntent {
      val intent = Intent(context, WeatherActivity::class.java)
      return PendingIntent.getActivity(context, value, intent, 0)
    }


  }
}

