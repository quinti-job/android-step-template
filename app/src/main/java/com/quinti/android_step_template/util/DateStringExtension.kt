package com.quinti.android_step_template.util

import android.content.Context
import com.quinti.android_step_template.R
import com.quinti.android_step_template.kmp.data.entity.RemainingTimeType

fun RemainingTimeType.getRemainingTimeText(context: Context): String {
    return when (this) {
        is RemainingTimeType.InDays -> context.getString(
            R.string.reward_remaining_days,
            days,
        )

        is RemainingTimeType.InTime -> context.getString(
            R.string.reward_remaining_date_times,
            hours,
            minute,
            second,
        )

        RemainingTimeType.Gone -> context.getString(R.string.reward_remaining_past)
        RemainingTimeType.Over -> context.getString(R.string.reward_remaining_past)
    }
}