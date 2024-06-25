package com.quinti.android_step_template.util

import korlibs.time.DateTime
import korlibs.time.DateTimeTz
import korlibs.time.TimezoneOffset
import korlibs.time.hours
import korlibs.time.offset

/**
 * JST +9:00 Offset
 */
val TimezoneOffset.Companion.JST: TimezoneOffset
    get() = 9.hours.offset

/**
 * DateTime の時刻を JST +9:00 とみなして
 * DateTimeTz を生成する
 */
fun DateTime.jst(): DateTimeTz {
    return toOffsetUnadjusted(TimezoneOffset.JST)
}

/**
 * Stringから JST +9:00 として DateTimeTz を生成する。
 * パースできる文字列は[DateTime.parse]を参照。
 */
fun DateTime.Companion.parseToJst(str: String): DateTimeTz {
    val dateTimeTz = parse(str)
    return dateTimeTz.toOffsetUnadjusted(TimezoneOffset.JST)
}