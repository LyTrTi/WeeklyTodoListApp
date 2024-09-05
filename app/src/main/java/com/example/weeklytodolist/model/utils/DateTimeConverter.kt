package com.example.weeklytodolist.model.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.LocalDateTime

@TypeConverters
object DateTimeConverter {
    @TypeConverter
    fun dateToString(dateTime: LocalDateTime): String? {
        val dateTimeString = dateTime.format(DateFormatInfo.Date.DATE_FORMAT_PATTERN)
        return dateTimeString
    }

    @TypeConverter
    fun stringToDate(date: String?): LocalDateTime {
        return LocalDateTime.parse(date, DateFormatInfo.Date.DATE_FORMAT_PATTERN)
    }
}