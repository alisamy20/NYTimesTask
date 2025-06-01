package com.example.common_kotlin.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun String.formatDate(): String {
    return try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        val date = parser.parse(this)
        formatter.format(date)
    } catch (e: Exception) {
        this // fallback if parsing fails
    }
}
