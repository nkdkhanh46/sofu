package com.martin.sofu.utils

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {
    companion object {
        fun timestampToDisplayDate(timestamp: Long): String {
            if (timestamp <=0) return ""

            val cal = Calendar.getInstance()
            cal.timeInMillis = timestamp*1000

            val sdf = SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault())
            return sdf.format(cal.time)
        }
    }
}