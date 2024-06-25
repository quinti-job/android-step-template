package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.DailyRouletteOptions
import kotlinx.serialization.Serializable

@Serializable
data class DailyRouletteOptionsResponse(
    val options: List<Long>,
) {

    fun toEntity(): DailyRouletteOptions {
        return DailyRouletteOptions(
            coinAmounts = options,
        )
    }
}