package com.quinti.android_step_template.kmp.data.entity

data class UncheckedPrizeSummary(
    val count: Int,
    val prize: PrizePreview,
) {
    data class PrizePreview(
        val prizeId: String,
        val imageUrl: String,
        val title: String,
        val entryCoinAmount: Long,
    )
}