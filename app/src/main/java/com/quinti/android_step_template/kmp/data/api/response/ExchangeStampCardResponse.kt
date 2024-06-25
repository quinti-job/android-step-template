package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.repository.StampCardExchangeResult
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeStampCardResponse(
    val totalCoin: Long = 0,
    val totalPoint: Long = 0,
) {
    fun toEntity(): StampCardExchangeResult {
        return StampCardExchangeResult(
            totalCoin = totalCoin,
            totalPoint = totalPoint,
        )
    }
}