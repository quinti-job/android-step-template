package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz

data class StampCardList(
    val nextLastCreatedAt: String?,
    val stampcards: List<StampCard>,
)

data class StampCard(
    val id: String,
    val stamps: List<Stamp>,
    val isCompleted: Boolean,
) {
    val hasNotExchangedStamp: Boolean = stamps.any { it.isStamped && !it.isExchanged }
}

data class Stamp(
    val type: Type,
    val stampedAt: DateTimeTz?,
    val exchangedAt: DateTimeTz?,
    val reward: Reward,
) {
    val isStamped: Boolean = stampedAt != null
    val isExchanged: Boolean = exchangedAt != null

    sealed class Type {
        data object Normal : Type()
        data class Bonus(val title: String) : Type()
    }

    sealed class Reward {
        data class Coin(val amount: Long) : Reward()
        data class Point(
            val amount: Long?,
            val options: List<Long>,
        ) : Reward() {
            val isUndefined = amount == null
        }

        data object Unknown : Reward()
    }
}
