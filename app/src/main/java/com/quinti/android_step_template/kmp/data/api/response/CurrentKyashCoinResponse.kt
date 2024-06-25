package com.quinti.android_step_template.kmp.data.api.response

import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class CurrentKyashCoinResponse(
    val availableAmount: Long,
    val pendingAmount: Long,
    val recentExpiry: CoinRecentExpiryResponse?,
)

@Serializable
data class CoinRecentExpiryResponse(
    val amount: Long,
    // "expireAt": "2023-04-30T23:59:59.999999+09:00"
    @Contextual val expireAt: DateTimeTz,
)