package com.chingari.chingariweatherdemo.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class DateUtils {

    fun formatDate(date: String): String? {
        @SuppressLint("SimpleDateFormat") val formatter =
            SimpleDateFormat("hh:mm dd/MM/yyyy")
        return formatter.format(Date(date.toLong() * 1000))
    }

    fun formatDate(date: Date?): String? {
        @SuppressLint("SimpleDateFormat") val formatter =
            SimpleDateFormat("hh:mm:ss dd/MM/yyyy")
        return formatter.format(date)
    }
}