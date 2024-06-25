package com.quinti.android_step_template.ui.component

import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class PointBalance(
    val availableAmount: Long,
    val pendingAmount: Long,
    @Contextual
    val expiresAt: DateTimeTz? = null,
) {
    val hasPoint: Boolean = availableAmount > 0
}