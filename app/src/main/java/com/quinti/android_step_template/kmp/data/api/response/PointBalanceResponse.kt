package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.ui.component.PointBalance
import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PointBalanceResponse(
    @SerialName("availableAmount")
    val availableAmount: Long,
    @SerialName("pendingAmount")
    val pendingAmount: Long,
    @SerialName("expiresAt")
    @Contextual
    val expiresAt: DateTimeTz? = null,
) {
    fun toDomainModel(): PointBalance {
        return PointBalance(
            availableAmount = availableAmount,
            pendingAmount = pendingAmount,
            expiresAt = expiresAt,
        )
    }
}