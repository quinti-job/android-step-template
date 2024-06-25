package com.quinti.android_step_template.kmp.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class UncheckedCoinPrizeResponse(
    val count: Int,
    val displayCoinPrize: PrizePreviewResponse?,
) {
    @Serializable
    data class PrizePreviewResponse(
        val id: String,
        val imageUrl: String,
        val title: String,
        val entryCoinAmount: Long,
    )
}