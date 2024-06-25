package com.quinti.android_step_template.util

import android.content.Context
import android.text.format.DateUtils
import com.quinti.android_step_template.R
import dagger.Reusable
import java.util.*
import javax.inject.Inject
import org.threeten.bp.Duration
import java.time.Instant
import java.time.ZoneId

/**
 * Formatter for date and time
 *
 * JvmStatic is required to use in data binding layout.
 */
@Reusable
class DateFormatter @Inject constructor(
    private val context: Context,
) {
    companion object {
        /**
         * yyyy/MM/dd HH:mm
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toDateTimeText(context: Context, date: Date?): String {
            if (date != null) {
                val flags = DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_YEAR or
                        DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_TIME
                return DateUtils.formatDateTime(context, date.time, flags)
            } else {
                return ""
            }
        }

        /**
         * MM/dd HH:mm
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toMonthDayDateTimeText(context: Context, date: Date?): String =
            if (date != null) {
                val flags = DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_DATE or
                        DateUtils.FORMAT_SHOW_TIME
                DateUtils.formatDateTime(context, date.time, flags)
            } else {
                ""
            }

        /**
         * yyyy/MM/dd
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toDateText(context: Context, date: Date): String {
            val flags = DateUtils.FORMAT_NUMERIC_DATE or DateUtils.FORMAT_SHOW_YEAR
            return DateUtils.formatDateTime(context, date.time, flags)
        }

        /**
         * yyyy/MM/dd or yyyy年M月d日
         * Localeによって表現が変わる
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toLocaleDateText(context: Context, date: Date): String {
            val flags = DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            return DateUtils.formatDateTime(context, date.time, flags)
        }

        /**
         * MM/dd
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toNoYearDateText(context: Context, date: Date?): String {
            if (date == null) return ""

            val flags = DateUtils.FORMAT_ABBREV_MONTH or DateUtils.FORMAT_SHOW_DATE
            return DateUtils.formatDateTime(context, date.time, flags)
        }

        /**
         * HH:mm
         */
        @JvmStatic
        @Deprecated("Should inject DateFormatter instead")
        fun toHHmm(context: Context, date: Date?): String {
            if (date == null) return ""

            val flags = DateUtils.FORMAT_SHOW_TIME
            return DateUtils.formatDateTime(context, date.time, flags)
        }

        /**
         * Get duration text between two date times.
         * ex: 2日12時間34分
         */
        @Deprecated("Should inject DateFormatter instead")
        fun toDuration(context: Context, date1: Date, date2: Date): String {
            val ldt1 =
                Instant.ofEpochMilli(date1.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val ldt2 =
                Instant.ofEpochMilli(date2.time).atZone(ZoneId.systemDefault()).toLocalDateTime()
            val duration = Duration.between(ldt1, ldt2)
            var text = ""
            if (duration.toDays() > 0) {
                text += context.getString(R.string.datetime_duration_format_day, duration.toDays())
            }
            if (duration.toHours() > 0) {
                if (text.isNotEmpty()) {
                    text += " "
                }
                text += context.getString(
                    R.string.datetime_duration_format_hour,
                    duration.toHours() % 24,
                )
            }
            if (duration.toMinutes() > 0) {
                if (text.isNotEmpty()) {
                    text += " "
                }
                text += context.getString(
                    R.string.datetime_duration_format_minute,
                    duration.toMinutes() % 60,
                )
            }
            return text
        }

        private fun isBefore(date1: Date, date2: Date): Boolean {
            return date1.time < date2.time
        }
    }

    fun toDateTimeText(date: Date?) = toDateTimeText(context, date)
    fun toDateText(date: Date) = toDateText(context, date)
    fun toLocaleDateText(date: Date) = toLocaleDateText(context, date)
    fun toNoYearDateText(date: Date?) = toNoYearDateText(context, date)
    fun toHHmm(date: Date?) = toHHmm(context, date)
    fun toDuration(date1: Date, date2: Date) = toDuration(context, date1, date2)
}