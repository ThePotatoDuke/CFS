package com.example.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.TextStyle
import java.util.Locale

class OffsetDateTimeFormatter {
    companion object StartDateParser {
        @RequiresApi(Build.VERSION_CODES.O)
        fun parse(date: OffsetDateTime): String {
            val dayOfWeek = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val dayOfMonth = date.dayOfMonth
            val month = date.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            val year = date.year

            return "$dayOfWeek, $month $dayOfMonth, $year"
        }
    }
}