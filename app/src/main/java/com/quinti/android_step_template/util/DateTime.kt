package com.quinti.android_step_template.util

/**
 * TimeZoneを含まない日付情報
 *
 * klockのDateTimeはiOSで使えないため、独自でDateTimeを用意する。
 */
data class DateTime(
    val year: Int,
    val month: Int,
    val day: Int,
) : Comparable<DateTime> {

    override fun compareTo(other: DateTime): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> day - other.day
        }
    }
}

fun korlibs.time.DateTime.toDateTime(): DateTime {
    return DateTime(yearInt, month1, dayOfMonth)
}

