package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

/**
 * 懸賞
 */
@Serializable
data class Prize(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val entryCoinAmount: Long,
    val appliedCount: Int,
    val winnersCount: Int,
    val maxWinnersCount: Int,
    val applicationStatus: ApplicationStatus,
    val activationStatus: ActivationStatus,
    val type: Type,
    val giftType: GiftType,
    @Contextual val startAt: DateTimeTz,
    @Contextual val endAt: DateTimeTz,
    @Contextual val announceAt: DateTimeTz,
    val lotteryResultChecked: Boolean,
) {
    /**
     * 与えられたコインで懸賞応募可能かどうか
     */
    fun canApply(kyashCoinAmount: Long): Boolean {
        return entryCoinAmount <= kyashCoinAmount
    }

    /**
     * 懸賞応募のために不足しているコイン数を計算する。
     *
     * @param kyashCoinAmount 保持しているコイン数
     * @return 不足しているコイン数
     */
    fun remainingKyashCoinAmount(kyashCoinAmount: Long): Long {
        return if (canApply(kyashCoinAmount)) {
            0L
        } else {
            entryCoinAmount - kyashCoinAmount
        }
    }

    /**
     * 保持しているコイン数に対する懸賞応募コイン数の比率を取得する。
     * 懸賞応募コイン数が0の場合は1を返す。
     * それ以外で、保持しているコイン数が0の場合は0を返す。
     * それ以外の場合は保持しているコイン数に対する懸賞応募コイン数の比率を返す。
     *
     * @param kyashCoinAmount 保持しているコイン数
     *
     * @return 保持しているコイン数に対する懸賞応募コイン数の比率
     */
    fun getPossessionToEntryCoinRatio(kyashCoinAmount: Long): Float {
        return when {
            entryCoinAmount == 0L -> 1f
            kyashCoinAmount == 0L -> 0f
            else -> {
                val rate = kyashCoinAmount / entryCoinAmount.toFloat()
                when {
                    rate >= 1.0f -> 1.0f
                    else -> rate
                }
            }
        }
    }

    /**
     * 当選者数の上限
     * - Welcome懸賞の場合は無制限（表示しない）
     * - 0の場合は無制限
     * - それ以外は上限数
     */
    val maxWinners: MaxWinners = when {
        type == Type.Welcome -> MaxWinners.Unlimited
        maxWinnersCount == 0 -> MaxWinners.Unlimited
        else -> MaxWinners.Limited(maxWinnersCount)
    }

    /**
     * 懸賞に応募しているかどうか
     */
    val isApplied: Boolean
        get() = applicationStatus.isApplied

    /**
     * 懸賞に応募できるかどうか
     *
     * * 応募枠が空いている
     * * 懸賞期間中
     * * 懸賞に未応募
     */
    val isApplicable: Boolean
        get() = activationStatus == ActivationStatus.Active &&
                hasEntrySeat &&
                applicationStatus.applicationEnabled

    /**
     * 応募枠が開いているかどうか
     */
    val hasEntrySeat: Boolean
        get() = when (maxWinners) {
            is MaxWinners.Limited -> winnersCount < maxWinnersCount
            MaxWinners.Unlimited -> true
        }

    /**
     * 懸賞の当落結果をまだ確認していないかどうか
     *
     * 当落結果の発表後かつ、当落結果をまだ確認していない場合はtrueを返す。
     *
     * @return まだ確認していない場合はtrue。確認済みの場合または当落結果発表前はfalse
     */
    val isLotteryResultNotChecked: Boolean =
        activationStatus == ActivationStatus.Announced && !lotteryResultChecked

    enum class ActivationStatus {
        Active,
        Inactive,
        Ended,
        Announced,

        ;
    }

    @Serializable
    sealed class ApplicationStatus {
        val applicationEnabled: Boolean
            get() = this is NotApplied

        val isApplied: Boolean
            get() = !applicationEnabled

        @Serializable
        data object NotApplied : ApplicationStatus()

        @Serializable
        data class Applied(
            @Contextual val appliedAt: DateTimeTz,
            val applicationId: String,
        ) : ApplicationStatus()

        @Serializable
        data class Lost(
            @Contextual val appliedAt: DateTimeTz,
            @Contextual val lotteryResultCheckExpireAt: DateTimeTz,
            val applicationId: String,
        ) : ApplicationStatus()

        @Serializable
        data class Won(
            @Contextual val appliedAt: DateTimeTz,
            @Contextual val lotteryResultCheckExpireAt: DateTimeTz,
            @Contextual val receiveExpireAt: DateTimeTz,
            val applicationId: String,
        ) : ApplicationStatus()

        @Serializable
        data class Received(
            @Contextual val appliedAt: DateTimeTz,
            @Contextual val receivedAt: DateTimeTz,
            val applicationId: String,
        ) : ApplicationStatus()

        @Serializable
        data class ReceiveExpired(
            @Contextual val appliedAt: DateTimeTz,
        ) : ApplicationStatus()

        // 確認期限切れ
        @Serializable
        data class LotteryResultCheckExpired(
            @Contextual val appliedAt: DateTimeTz,
        ) : ApplicationStatus()
    }

    enum class Type {
        Weekly,
        Daily,
        Welcome,

        ;
    }

    @Serializable
    sealed class MaxWinners {
        data object Unlimited : MaxWinners()
        data class Limited(val count: Int) : MaxWinners()
    }

    @Serializable
    sealed class GiftType {
        @Serializable
        data object Redirect : GiftType()

        @Serializable
        sealed class Auto : GiftType() {
            @Serializable
            data class Point(val amount: Long) : Auto()
        }
    }
}