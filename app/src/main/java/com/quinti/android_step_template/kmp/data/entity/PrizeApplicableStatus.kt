package com.quinti.android_step_template.kmp.data.entity

/**
 * 懸賞応募可能状態
 */
sealed class PrizeApplicableStatus {
    /**
     * 懸賞応募可能
     */
    data object Applicable : PrizeApplicableStatus()

    /**
     * 懸賞応募済み
     */
    data object Applied : PrizeApplicableStatus()

    /**
     * チャレンジ終了
     */
    data class Ended(val reason: Reason) : PrizeApplicableStatus() {
        enum class Reason {
            /**
             * 懸賞期間終了
             */
            LotteryPeriodEnded,

            /**
             * 枠不足
             */
            NoRemainingWinners,
        }
    }

    /**
     * まだ応募できない（Coming Soon）
     */
    data object NotYetApplicable : PrizeApplicableStatus()

    /**
     * コイン不足
     */
    data class Shortage(val missingAmount: Long, val progress: Float) : PrizeApplicableStatus()

    companion object {

        /**
         * 懸賞ラベル状態
         * * 準備中 → `Coming Soon`
         * * 応募可能 → `応募できます`
         * * 当選枠なし→ `チャレンジ終了(当選枠なし)`
         * * チャレンジ終了 → `チャレンジ終了(懸賞終了)`
         * * 応募不可（コイン不足）→ `あとnコイン！`
         * * その他 → `応募済`
         */
        operator fun invoke(
            prizeChallengeStatus: RewardChallengeStatus,
        ): PrizeApplicableStatus {
            return when (prizeChallengeStatus) {
                is RewardChallengeStatus.Upcoming -> NotYetApplicable
                is RewardChallengeStatus.Applicable -> Applicable
                RewardChallengeStatus.Ended -> Ended(
                    Ended.Reason.LotteryPeriodEnded,
                )

                is RewardChallengeStatus.Filled -> Ended(
                    Ended.Reason.NoRemainingWinners,
                )

                is RewardChallengeStatus.Shortage -> Shortage(
                    prizeChallengeStatus.missingAmount,
                    prizeChallengeStatus.progress,
                )

                is RewardChallengeStatus.Applied,
                is RewardChallengeStatus.ResultAnnounced,
                is RewardChallengeStatus.CheckExpired,
                is RewardChallengeStatus.Lost,
                is RewardChallengeStatus.Won,
                is RewardChallengeStatus.ReceiveExpired,
                is RewardChallengeStatus.Received,
                -> Applied
            }
        }
    }
}