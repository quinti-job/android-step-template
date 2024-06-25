package com.quinti.android_step_template.kmp.data.entity


data class StampCardUiState(
    val stampCard: StampCard,
) {
    val stamps: List<StampUiState> = stampCard.stamps.mapIndexed { index, stamp ->
        StampUiState(stamp, index + 1)
    }
    val hasNeedStampAnimation: Boolean =
        stamps.filter { !it.reward.needRouletteAnimation }.any { it.needAnimation }
    val hasNeedRouletteAnimation: Boolean =
        stamps.filter { it.reward.needRouletteAnimation }.any { it.needAnimation }
    val isCompleted: Boolean = stampCard.isCompleted
}

data class StampUiState(
    val stamp: Stamp,
    val currentCount: Int,
) {
    val isStamped: Boolean = stamp.isStamped
    val isExchanged: Boolean = stamp.isExchanged
    val needAnimation: Boolean = isStamped && !isExchanged
    val reward: Stamp.Reward = stamp.reward
}

val Stamp.Reward.needRouletteAnimation: Boolean
    get() = when (this) {
        Stamp.Reward.Unknown -> false
        is Stamp.Reward.Coin -> false
        is Stamp.Reward.Point -> true
    }

