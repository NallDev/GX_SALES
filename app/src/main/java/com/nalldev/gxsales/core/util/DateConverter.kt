package com.nalldev.gxsales.core.util

import android.text.format.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateConverter {
    fun getToday(): String {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Date())
    }

    fun convertDDMMYYYYToDMMMYYYY(fromDate : String): String {
        val originalFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val targetFormat = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        val toDate = originalFormat.parse(fromDate)
        return targetFormat.format(toDate ?: Date())
    }

    fun convertLongToDateString(time: Long, format: String = "dd/MM/yyyy"): String {
        return DateFormat.format(format, time).toString()
    }
}