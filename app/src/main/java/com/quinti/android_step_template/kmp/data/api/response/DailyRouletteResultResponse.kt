package com.quinti.android_step_template.kmp.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class DailyRouletteResultResponse(
    val winningAmount: Long,
)