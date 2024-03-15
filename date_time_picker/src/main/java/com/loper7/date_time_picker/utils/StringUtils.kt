package com.loper7.date_time_picker.utils

import android.content.Context
import android.text.format.DateFormat
import java.lang.StringBuilder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalField
import java.util.*

/**
 * 字符串操作工具包
 *
 */
internal object StringUtils {
    fun conversionTime(
        time: String,
        format: String = "yyyy-MM-dd HH:mm:ss"
    ): Long {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val ofPattern = DateTimeFormatter.ofPattern(format)
            return LocalDateTime.parse(time, ofPattern).toInstant(ZoneOffset.ofHours(8))
                .toEpochMilli()
        } else {
            val sdf = SimpleDateFormat(format, Locale.getDefault())
            try {
                return sdf.parse(time)?.time?:0
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0
        }
    }

    /**
     * @param time
     * @return yy-MM-dd HH:mm格式时间
     */
    fun conversionTime(time: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
        return DateFormat.format(format, time).toString()
    }
}
