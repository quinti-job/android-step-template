package com.quinti.android_step_template.util

import korlibs.time.DateTime
import korlibs.time.DateTimeTz
import korlibs.time.MonthSpan
import korlibs.time.TimezoneOffset
import korlibs.time.YearMonth

/**
 * 指定した日付([specifiedDay])が次に来る日時を[DateTimeTz]として返す。
 *
 * 例えば、2023年1月10日に対して、[specifiedDay]に11を指定した場合、2023年1月11日の[DateTimeTz]を返す。
 * 2023年1月10日に対して、[specifiedDay]に1を指定した場合、2023年2月1日の[DateTimeTz]を返す。
 *
 * 指定した日付が、その月に存在しない場合は、その月の最終日を返す。
 * 指定した日付が当日だった場合は翌月の対象日時になる。
 */
fun DateTimeTz.nextAutoRepaymentDate(specifiedDay: Int): DateTimeTz {
    return if (dayOfMonth < specifiedDay) {
        val hasSpecifiedDay = yearMonth.daysOfMonth.contains(specifiedDay)
        if (hasSpecifiedDay) {
            // 今月の指定日
            DateTime(year, month, specifiedDay).toOffsetUnadjusted(offset)
        } else {
            // 今月の月末の日時を返す
            DateTime(year, month, yearMonth.days).toOffsetUnadjusted(offset)
        }
    } else {
        val nextMonth = yearMonth.plus(MonthSpan(1))
        val hasSpecifiedDay = nextMonth.daysOfMonth.contains(specifiedDay)
        // 翌月の指定日の日時を返す
        if (hasSpecifiedDay) {
            // 翌月の指定日
            DateTime(nextMonth.year, nextMonth.month, specifiedDay).toOffsetUnadjusted(offset)
        } else {
            // 翌月の月末の日時を返す
            DateTime(nextMonth.year, nextMonth.month, nextMonth.days).toOffsetUnadjusted(offset)
        }
    }
}

/**
 * 指定した時刻に置き換えた[DateTimeTz]を返す
 */
fun DateTimeTz.replaceTime(
    hours: Int? = null,
    minutes: Int? = null,
    seconds: Int? = null,
    milliseconds: Int? = null,
): DateTimeTz {
    return DateTime(
        year,
        month,
        dayOfMonth,
        hours ?: this.hours,
        minutes ?: this.minutes,
        seconds ?: this.seconds,
        milliseconds ?: this.milliseconds,
    )
        .toOffsetUnadjusted(offset)
}

/**
 * 年月に含まれる日付のリストを返す。
 */
val YearMonth.daysOfMonth: List<Int>
    get() = (1..days).toList()

object DateUtil {

    /**
     * JSTでの現在時刻を取得する
     */
    fun nowJst(): DateTimeTz {
        return DateTime.now().toOffset(TimezoneOffset.JST)
    }
}