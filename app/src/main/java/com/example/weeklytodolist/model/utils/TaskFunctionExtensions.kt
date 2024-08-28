package com.example.weeklytodolist.model.utils

import com.example.weeklytodolist.model.Task

fun Task.getDate(): String {
    return dateTime.substring(
        DateFormatInfo.Date.indexes.first,
        DateFormatInfo.Date.indexes.second
    )
}

fun Task.getTime(): String {
    return dateTime.substring(
        DateFormatInfo.Time.indexes.first,
        DateFormatInfo.Time.indexes.second
    )
}

fun Task.getDayOfWeek(): String {
    return dateTime.substring(
        DateFormatInfo.DayOfWeek.FIRST_INDEX
    )
}