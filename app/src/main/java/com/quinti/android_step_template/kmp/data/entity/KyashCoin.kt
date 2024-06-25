package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz

data class KyashCoin(
    val availableAmount: Long,
    val pendingAmount: Long,
    val recentExpiry: Expiry?,
) {
    data class Expiry(
        val amount: Long,
        val expireAt: DateTimeTz,
    )
}