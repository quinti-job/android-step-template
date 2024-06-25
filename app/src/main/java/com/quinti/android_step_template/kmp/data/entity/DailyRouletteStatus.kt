package com.quinti.android_step_template.kmp.data.entity

sealed class DailyRouletteStatus {
    val available: Boolean
        get() = this is Available

    data object Unavailable: DailyRouletteStatus()
    data object Available: DailyRouletteStatus()
    data object Done: DailyRouletteStatus()
}