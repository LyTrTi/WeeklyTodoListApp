package com.example.weeklytodolist.model.utils

import android.icu.text.DateFormatSymbols
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


object DateFormatInfo {
    private const val TIME_LENGTH = 9
    private const val DATE_LENGTH = 10

    object DateTime {
        val DATETIME_FORMAT_PATTERN: DateTimeFormatter =
            DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy EEEE")
    }

    object Time {
        val TIME_FORMAT_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val indexes: Pair<Int, Int> = Pair(0, TIME_LENGTH)
    }

    object Date {
        val DATE_FORMAT_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val indexes: Pair<Int, Int> = Pair(TIME_LENGTH + 1, TIME_LENGTH + DATE_LENGTH)
    }

    object DayOfWeek {
        val DoW_FORMAT_PATTERN: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE")
        const val FIRST_INDEX = TIME_LENGTH + DATE_LENGTH + 1
    }

    fun milliSecondToLocalDateTime(milliSecond: Long): LocalDateTime {
        return Instant.ofEpochMilli(milliSecond).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    fun currentDateTime(): String {
        return LocalDateTime.now().format(DateTime.DATETIME_FORMAT_PATTERN)
    }

    fun currentDate(): String {
        return LocalDateTime.now().format(Date.DATE_FORMAT_PATTERN)
    }

    fun currentTime(): String {
        return LocalDateTime.now().format(Time.TIME_FORMAT_PATTERN)
    }

    fun currentDayOfWeek(): String {
        return LocalDateTime.now().format(DayOfWeek.DoW_FORMAT_PATTERN)
    }
}