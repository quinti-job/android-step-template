package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.ApplyCoinPrizeResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApplyCoinPrizeResponse(
    @SerialName("coinPrize") val coinPrize: CoinPrizeResponse,
) {
    fun toEntity(): ApplyCoinPrizeResult {
        return ApplyCoinPrizeResult(
            prize = coinPrize.toEntity(),
        )
    }
}