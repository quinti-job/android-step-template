package com.quinti.android_step_template.kmp.data.api.response

import co.kyash.mobile.model.reward.ReceivePrizeType
import kotlinx.serialization.Serializable

@Serializable
data class ReceivePrizeResponse(
    private val type: String,
    private val redirectUrl: String?,
) {
    fun toEntity(): ReceivePrizeType {
        return when {
            type == "redirect" &&
                    redirectUrl != null -> ReceivePrizeType.Redirect(redirectUrl)
            else -> ReceivePrizeType.Unknown
        }
    }
}