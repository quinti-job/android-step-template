package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.Stamp
import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.entity.StampCardList
import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class StampCardListResponse(
    val nextLastCreatedAt: String?,
    val stampcards: List<StampCardResponse>,
) {
    fun toEntity(): StampCardList {
        return StampCardList(
            stampcards = stampcards.map { it.toEntity() },
            nextLastCreatedAt = nextLastCreatedAt,
        )
    }
}

@Serializable
data class StampCardResponse(
    val id: String,
    val stamps: List<StampResponse>,
    @Contextual val completedAt: DateTimeTz?,
) {
    fun toEntity(): StampCard {
        return StampCard(
            id = id,
            stamps = stamps.map { it.toEntity() },
            isCompleted = completedAt != null,
        )
    }
}

@Serializable
data class StampResponse(
    val type: String,
    val title: String,
    @Contextual val stampedAt: DateTimeTz?,
    @Contextual val exchangedAt: DateTimeTz?,
    val reward: StampRewardResponse,
) {
    fun toEntity(): Stamp {
        return Stamp(
            type = when (type) {
                "normal" -> Stamp.Type.Normal
                "bonus" -> Stamp.Type.Bonus(title)
                else -> Stamp.Type.Normal
            },
            stampedAt = stampedAt,
            exchangedAt = exchangedAt,
            reward = reward.toEntity(stampedAt != null),
        )
    }
}

@Serializable
data class StampRewardResponse(
    val type: String,
    val amount: Long? = null,
    val amountOptions: List<Long> = emptyList(),
) {
    fun toEntity(isStamped: Boolean): Stamp.Reward {
        return when (type) {
            "coin" -> Stamp.Reward.Coin(
                amount = if (isStamped) {
                    // スタンプが押されている場合（獲得予定コインが確定している場合）はその値を使用する
                    amount
                } else {
                    // スタンプが押されていない場合はamountOptionsの最初の値を使用する
                    // type=coinの場合、amountOptionsは常に1つ以上の値が入っている
                    amountOptions.firstOrNull()
                } ?: 0,
            )
            // ポイントはそのまま利用する
            "point" -> Stamp.Reward.Point(amount, amountOptions)
            else -> Stamp.Reward.Unknown
        }
    }
}