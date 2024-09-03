package com.example.weeklytodolist.ui.home.fragment

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.weeklytodolist.model.utils.DateFormatInfo

//enum class DayOfWeek {
//    Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
//}

class DateListViewModel : ViewModel() {
    var uiState by mutableStateOf(DateFormatInfo.currentDayOfWeek())

    fun onChanged(selectedDay: String) {
        uiState = selectedDay
    }
}