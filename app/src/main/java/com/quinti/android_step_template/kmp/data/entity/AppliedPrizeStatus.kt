package com.quinti.android_step_template.kmp.data.entity

import com.quinti.android_step_template.util.replaceTime
import korlibs.time.DateTimeTz

/**
 * 申込み完了( applied )
 * 結果未確認( result_not_checked )
 * 当選( won )
 * 落選( lost )
 * 当選確認期限切れ( check_expired )
 * 受取URL発行( received )
 * 受取期限切れ( receive_expired )
 */
sealed class AppliedPrizeStatus {

    /**
     * 申込み前( not_applied )
     */
    data object NotApplied : AppliedPrizeStatus()

    /**
     * 申込み完了( applied )
     */
    data class Applied(
        val appliedAt: DateTimeTz,
        private val announceAt: DateTimeTz,
        private val now: DateTimeTz = DateTimeTz.nowLocal(),
    ) : AppliedPrizeStatus() {
        val remainingAnnounceAt = RemainingTimeType.differenceAsRemainingTime(
            announceAt,
            now.replaceTime(0, 0),
        )
    }

    /**
     * 結果未確認( result_not_checked )
     */
    data class ResultNotChecked(
        val appliedAt: DateTimeTz,
        val result: PrizeApplicationResult,
        private val checkExpireAt: DateTimeTz,
        private val now: DateTimeTz = DateTimeTz.nowLocal(),
    ) : AppliedPrizeStatus() {
        val remainingCheckExpireAt = RemainingTimeType.differenceAsRemainingTime(
            checkExpireAt,
            now.replaceTime(0, 0),
        )
    }

    /**
     * 当選( won )
     */
    data class Won(
        val appliedAt: DateTimeTz,
        private val receiveExpireAt: DateTimeTz,
        private val now: DateTimeTz = DateTimeTz.nowLocal(),
    ) : AppliedPrizeStatus() {
        val remainingReceiveExpireAt = RemainingTimeType.differenceAsRemainingTime(
            receiveExpireAt,
            now.replaceTime(0, 0),
        )
    }

    /**
     * 落選( lost )
     */
    data class Lost(
        val appliedAt: DateTimeTz,
    ) : AppliedPrizeStatus()

    /**
     * 当選確認期限切れ( check_expired )
     */
    data class CheckExpired(
        val appliedAt: DateTimeTz,
    ) : AppliedPrizeStatus()

    /**
     * 受取URL発行( received )
     */
    data class Received(
        val appliedAt: DateTimeTz,
    ) : AppliedPrizeStatus()

    /**
     * 受取期限切れ( receive_expired )
     */
    data class ReceiveExpired(
        val appliedAt: DateTimeTz,
    ) : AppliedPrizeStatus()

    companion object {
        operator fun invoke(prize: Prize) = when (val status = prize.applicationStatus) {
            is Prize.ApplicationStatus.Applied -> Applied(
                appliedAt = status.appliedAt,
                announceAt = prize.announceAt,
            )

            is Prize.ApplicationStatus.LotteryResultCheckExpired -> CheckExpired(
                appliedAt = status.appliedAt,
            )

            is Prize.ApplicationStatus.ReceiveExpired -> ReceiveExpired(
                appliedAt = status.appliedAt,
            )

            is Prize.ApplicationStatus.Received -> Received(
                appliedAt = status.appliedAt,
            )

            is Prize.ApplicationStatus.Lost -> when (prize.isLotteryResultNotChecked) {
                true -> ResultNotChecked(
                    appliedAt = status.appliedAt,
                    result = PrizeApplicationResult.Lost(
                        prizeId = prize.id,
                        title = prize.title,
                        imageUrl = prize.imageUrl,
                        entryCoinAmount = prize.entryCoinAmount,
                        type = prize.type,
                        isChecked = prize.lotteryResultChecked,
                        applicationId = status.applicationId,
                    ),
                    status.lotteryResultCheckExpireAt,
                )

                false -> Lost(
                    appliedAt = status.appliedAt,
                )
            }

            is Prize.ApplicationStatus.Won -> when (prize.isLotteryResultNotChecked) {
                true -> ResultNotChecked(
                    appliedAt = status.appliedAt,
                    result = PrizeApplicationResult.Won(
                        prizeId = prize.id,
                        title = prize.title,
                        imageUrl = prize.imageUrl,
                        entryCoinAmount = prize.entryCoinAmount,
                        type = prize.type,
                        receiveExpireAt = status.receiveExpireAt,
                        applicationId = status.applicationId,
                        giftType = prize.giftType,
                        isChecked = prize.lotteryResultChecked,
                    ),
                    checkExpireAt = status.lotteryResultCheckExpireAt,
                )

                false -> Won(
                    appliedAt = status.appliedAt,
                    receiveExpireAt = status.receiveExpireAt,
                )
            }

            else -> NotApplied
        }
    }
}