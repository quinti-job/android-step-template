package com.quinti.android_step_template.util

import korlibs.time.DateTimeTz
import java.util.Date

fun DateTimeTz.toDate(): Date {
    return Date(this.utc.unixMillisLong)
}

fun Date.toDateTimeTz(): DateTimeTz {
    return DateTimeTz.fromUnix(this.time)
}