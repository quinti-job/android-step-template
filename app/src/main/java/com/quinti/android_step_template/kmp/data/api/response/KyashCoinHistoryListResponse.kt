package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistory
import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistoryList
import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class KyashCoinHistoryListResponse(
    val coinHistories: List<KyashCoinHistoryResponse>,
    val nextLastCreatedAt: String?,
) {
    fun toEntity(): KyashCoinHistoryList {
        return KyashCoinHistoryList(
            transactions = coinHistories.map { it.toEntity() },
            nextLastCreatedAt = nextLastCreatedAt,
        )
    }
}

@Serializable
data class KyashCoinHistoryResponse(
    val type: String,
    val title: String,
    val amount: Long,
    @Contextual val createdAt: DateTimeTz,
) {
    fun toEntity(): KyashCoinHistory {
        return when (this.type) {
            "received" -> KyashCoinHistory.Available.Received(
                amount = amount,
                createdAt = createdAt,
                title = title,
            )
            "consumed" -> KyashCoinHistory.Available.Consumed(
                amount = amount,
                createdAt = createdAt,
                title = title,
            )
            "expired" -> KyashCoinHistory.Available.Expired(
                amount = amount,
                createdAt = createdAt,
                title = title,
            )
            else -> KyashCoinHistory.Unknown
        }
    }
}