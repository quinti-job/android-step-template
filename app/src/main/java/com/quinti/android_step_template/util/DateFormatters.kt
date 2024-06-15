package com.quinti.android_step_template.util

import android.text.format.DateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.platform.LocalContext
import androidx.core.i18n.DateTimeFormatter
import androidx.core.i18n.DateTimeFormatterSkeletonOptions
import korlibs.time.DateTimeTz
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import korlibs.time.DateTime as KlockDateTime

/**
 * [Date]をLocaleに対応した年月日表現に変換する
 *
 * e.g. "2023年1月23日", "January 23, 2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearDate(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearDateOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した年月日表現に変換する
 *
 * e.g. "2023年1月23日", "January, 2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearDate(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearDateOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [DateTime]をLocaleに対応した年月日表現に変換する
 * 端末の時刻設定に依存しない表記にする場合はこれを利用する。
 *
 * e.g. "2023年1月23日", "January 23, 2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearDate(dateTime: DateTime): String {
    val utcMillis = KlockDateTime(dateTime.year, dateTime.month, dateTime.day).unixMillisLong
    val formatPattern = DateFormat.getBestDateTimePattern(
        Locale.getDefault(),
        "yyyyMMMMd",
    )
    // 端末の時刻設定に依存しないようにUTCの時間をUTCのTimeZoneでフォーマットする
    val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    calendar.timeInMillis = utcMillis
    return DateFormat.format(formatPattern, calendar).toString()
}

/**
 * [Date]をLocaleに対応した年月表現に変換する
 *
 * e.g. "2023年1月", "January, 2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearMonth(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearMonthOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した年月表現に変換する
 *
 * e.g. "2023年1月", "January 23, 2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearMonth(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearMonthOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [Date]をLocaleに対応した数字のみの年月日表現に変換する
 *
 * e.g. "2023/1/23", "1/23/2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleNumericYearDate(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleNumericYearDateOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した数字のみの年月日表現に変換する
 *
 * e.g. "2023/1/23", "1/23/2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleNumericYearDate(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleNumericYearDateOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [Date]をLocaleに対応した数字のみの年月日表現に変換する
 *
 * e.g. "2023/01/01", "01/01/2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleNumericTwoDigitYearDate(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleNumericTwoDigitYearDateOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した数字のみの年月日表現に変換する
 *
 * e.g. "2023/01/01", "01/01/2023"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleNumericTwoDigitYearDate(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleNumericTwoDigitYearDateOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [Date]をLocaleに対応した月日表現に変換する
 *
 * e.g. "1月23日", "Jan 23"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleDate(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleDateOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した月日表現に変換する
 *
 * e.g. "1月23日", "Jan 23"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleDate(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleDateOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [Date]をLocaleに対応した月日と時刻表現に変換する
 *
 * e.g. "1月23日 12:34", "Jan 23, 12:34"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleDateTime(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleDateTimeOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した月日と時刻表現に変換する
 *
 * e.g. "1月23日 12:34", "Jan 23, 12:34"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleDateTime(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleDateTimeOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

/**
 * [Date]をLocaleに対応した月日と時刻表現に変換する
 *
 * e.g. "2023年1月23日 12:34", "Jan 23, 2023, 12:34"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearDateTime(date: Date): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearDateTimeOptions,
    )
    return formatter.format(date)
}

/**
 * [DateTimeTz]をLocaleに対応した月日と時刻表現に変換する
 *
 * e.g. "2023年1月23日 12:34", "Jan 23, 2023, 12:34"
 */
@Composable
@ReadOnlyComposable
fun formatLocaleYearDateTime(dateTimeTz: DateTimeTz): String {
    val formatter = DateTimeFormatter(
        context = LocalContext.current,
        options = LocaleYearDateTimeOptions,
    )
    return formatter.format(dateTimeTz.utc.unixMillisLong)
}

private val LocaleYearDateOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setYear(DateTimeFormatterSkeletonOptions.Year.NUMERIC)
    .setMonth(DateTimeFormatterSkeletonOptions.Month.WIDE)
    .setDay(DateTimeFormatterSkeletonOptions.Day.NUMERIC)
    .build()

private val LocaleYearMonthOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setYear(DateTimeFormatterSkeletonOptions.Year.NUMERIC)
    .setMonth(DateTimeFormatterSkeletonOptions.Month.WIDE)
    .build()

private val LocaleNumericYearDateOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setYear(DateTimeFormatterSkeletonOptions.Year.NUMERIC)
    .setMonth(DateTimeFormatterSkeletonOptions.Month.NUMERIC)
    .setDay(DateTimeFormatterSkeletonOptions.Day.NUMERIC)
    .build()

private val LocaleNumericTwoDigitYearDateOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setYear(DateTimeFormatterSkeletonOptions.Year.NUMERIC)
    .setMonth(DateTimeFormatterSkeletonOptions.Month.TWO_DIGITS)
    .setDay(DateTimeFormatterSkeletonOptions.Day.TWO_DIGITS)
    .build()

private val LocaleDateOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setMonth(DateTimeFormatterSkeletonOptions.Month.ABBREVIATED)
    .setDay(DateTimeFormatterSkeletonOptions.Day.NUMERIC)
    .build()

private val LocaleDateTimeOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setMonth(DateTimeFormatterSkeletonOptions.Month.ABBREVIATED)
    .setDay(DateTimeFormatterSkeletonOptions.Day.NUMERIC)
    .setHour(DateTimeFormatterSkeletonOptions.Hour.FORCE_24H_NUMERIC)
    .setMinute(DateTimeFormatterSkeletonOptions.Minute.NUMERIC)
    .build()

private val LocaleYearDateTimeOptions = DateTimeFormatterSkeletonOptions.Builder()
    .setYear(DateTimeFormatterSkeletonOptions.Year.NUMERIC)
    .setMonth(DateTimeFormatterSkeletonOptions.Month.ABBREVIATED)
    .setDay(DateTimeFormatterSkeletonOptions.Day.NUMERIC)
    .setHour(DateTimeFormatterSkeletonOptions.Hour.FORCE_24H_NUMERIC)
    .setMinute(DateTimeFormatterSkeletonOptions.Minute.NUMERIC)
    .build()

@PreviewLocales
@Composable
private fun DateFormattersPreviews() {
    val date = Date()
    val dateTimeTz = DateTimeTz.nowLocal()
    Surface {
        Column {
            Text(formatLocaleYearDate(date))
            Text(formatLocaleYearDate(dateTimeTz))
            Text(formatLocaleYearMonth(date))
            Text(formatLocaleYearMonth(dateTimeTz))
            Text(formatLocaleNumericYearDate(date))
            Text(formatLocaleNumericYearDate(dateTimeTz))
            Text(formatLocaleDate(date))
            Text(formatLocaleDate(dateTimeTz))
            Text(formatLocaleDateTime(date))
            Text(formatLocaleDateTime(dateTimeTz))
            Text(formatLocaleYearDateTime(dateTimeTz))
            Text(formatLocaleYearDateTime(dateTimeTz))
        }
    }
}

