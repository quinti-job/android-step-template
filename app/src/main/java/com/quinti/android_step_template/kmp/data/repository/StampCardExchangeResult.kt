package com.quinti.android_step_template.kmp.data.repository

data class StampCardExchangeResult(
    val totalCoin: Long,
    val totalPoint: Long,
) {
    val hasTotalCoin = totalCoin > 0
    val hasTotalPoint = totalPoint > 0
}