package com.sgtech.noteapp.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getFormattedDateAndTime(millis: Long): String {
    val date = Date(millis)
    val format = SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault())
    return format.format(date)
}

fun getFormatedDate(millis: Long): String {
    val date = Date(millis)
    val format = SimpleDateFormat("dd MMM", Locale.getDefault())
    return format.format(date)
}