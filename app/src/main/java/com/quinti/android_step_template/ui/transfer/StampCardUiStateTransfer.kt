package com.quinti.android_step_template.ui.transfer

import android.os.Parcelable
import com.quinti.android_step_template.kmp.data.entity.Stamp
import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.entity.StampCardUiState
import korlibs.time.DateTimeTz
import kotlinx.parcelize.Parcelize

@Parcelize
data class StampCardUiStateTransfer(
    val stampCard: StampCardTransfer,
) : Parcelable {

    fun toEntity(): StampCardUiState {
        return StampCardUiState(
            stampCard = stampCard.toEntity(),
        )
    }
    companion object {
        fun from(uiState: StampCardUiState): StampCardUiStateTransfer {
            return StampCardUiStateTransfer(
                stampCard = StampCardTransfer.from(uiState.stampCard),
            )
        }
    }
}

@Parcelize
data class StampCardTransfer(
    val id: String,
    val stamps: List<StampTransfer>,
    val isCompleted: Boolean,
) : Parcelable {

    fun toEntity(): StampCard {
        return StampCard(
            id = id,
            stamps = stamps.map { it.toEntity() },
            isCompleted = isCompleted,
        )
    }

    companion object {
        fun from(card: StampCard): StampCardTransfer {
            return StampCardTransfer(
                id = card.id,
                stamps = card.stamps.map { StampTransfer.from(it) },
                isCompleted = card.isCompleted,
            )
        }
    }
}

@Parcelize
data class StampTransfer(
    val type: Type,
    val stampedAt: DateTimeTz?,
    val exchangedAt: DateTimeTz?,
    val reward: RewardTransfer,
) : Parcelable {
    fun toEntity(): Stamp {
        return Stamp(
            type = type.toEntity(),
            stampedAt = stampedAt,
            exchangedAt = exchangedAt,
            reward = reward.toEntity(),
        )
    }

    @Parcelize
    sealed class Type : Parcelable {
        @Parcelize
        data object Normal : Type()

        @Parcelize
        data class Bonus(val title: String) : Type()

        fun toEntity(): Stamp.Type {
            return when (this) {
                is Normal -> Stamp.Type.Normal
                is Bonus -> Stamp.Type.Bonus(title)
            }
        }

        companion object {
            fun from(type: Stamp.Type): Type {
                return when (type) {
                    is Stamp.Type.Normal -> Normal
                    is Stamp.Type.Bonus -> Bonus(type.title)
                }
            }
        }
    }

    companion object {
        fun from(stamp: Stamp): StampTransfer {
            return StampTransfer(
                type = Type.from(stamp.type),
                stampedAt = stamp.stampedAt,
                exchangedAt = stamp.exchangedAt,
                reward = RewardTransfer.from(stamp.reward),
            )
        }
    }

    @Parcelize
    sealed class RewardTransfer : Parcelable {
        @Parcelize
        data class Coin(val amount: Long) : RewardTransfer()

        @Parcelize
        data class Point(val amount: Long?, val options: List<Long>) : RewardTransfer()

        @Parcelize
        data object Unknown : RewardTransfer()

        fun toEntity(): Stamp.Reward {
            return when (this) {
                is Unknown -> Stamp.Reward.Unknown
                is Coin -> Stamp.Reward.Coin(amount)
                is Point -> Stamp.Reward.Point(amount, options)
            }
        }

        companion object {
            fun from(reward: Stamp.Reward): RewardTransfer {
                return when (reward) {
                    is Stamp.Reward.Coin -> Coin(reward.amount)
                    is Stamp.Reward.Point -> Point(reward.amount, reward.options)
                    is Stamp.Reward.Unknown -> Unknown
                }
            }
        }
    }
}