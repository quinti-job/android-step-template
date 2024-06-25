package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz

/**
 * 懸賞チャレンジ状態
 *
 * 懸賞開始〜応募〜受取を含む懸賞に関する取り得る状態を定義する。
 * 懸賞の状態（開始前、応募可能、終了、発表）と、
 * ユーザーの応募状態（応募済み、検証結果確認期限切れ、検証結果確認済み、当選、落選、受取期限切れ、受取済み）を組み合わせて表現する。
 * 特に、コイン不足はユーザーが保有するコイン数を必要とする。
 */
sealed class RewardChallengeStatus {
    /**
     * 準備中(Upcoming)
     */
    data class Upcoming(
        val startAt: DateTimeTz,
    ) : RewardChallengeStatus()

    /**
     * 応募可能(Applicable)
     */
    data class Applicable(
        val endAt: DateTimeTz,
    ) : RewardChallengeStatus()

    /**
     * コイン不足(Shortage)
     */
    // shortage
    data class Shortage(val missingAmount: Long, val progress: Float) : RewardChallengeStatus()

    /**
     * 当選枠なし(Filled)
     */
    data class Filled(val missingAmount: Long, val progress: Float) : RewardChallengeStatus()

    /**
     * 懸賞終了(Ended)
     */
    data object Ended : RewardChallengeStatus()

    /**
     * 応募済み(Applied)
     */
    data class Applied(
        val appliedAt: DateTimeTz,
        val announceAt: DateTimeTz,
        val applicationId: String,
    ) : RewardChallengeStatus()

    /**
     * 結果未確認(Announced)
     */
    data class ResultAnnounced(
        val appliedAt: DateTimeTz,
        val applicationId: String,
        val lotteryResultCheckExpireAt: DateTimeTz,
        val result: Result,
    ) : RewardChallengeStatus() {
        enum class Result {
            Lost,
            Won,
        }
    }

    /**
     * 確認期限切れ(CheckExpired)
     */
    data object CheckExpired : RewardChallengeStatus()

    /**
     * 落選(Lost)
     */
    data class Lost(
        val appliedAt: DateTimeTz,
        val applicationId: String,
    ) : RewardChallengeStatus()

    /**
     * 当選(Won), 受取手続き前
     */
    data class Won(
        val appliedAt: DateTimeTz,
        val applicationId: String,
        val receiveExpireAt: DateTimeTz,
    ) : RewardChallengeStatus()

    /**
     * 受取手続き完了済み(Received)
     */
    data object Received : RewardChallengeStatus()

    /**
     * 受取期限切れ(ReceiveExpired)
     */
    data object ReceiveExpired : RewardChallengeStatus()

    companion object {
        operator fun invoke(prize: Prize, kyashCoinAmount: Long): RewardChallengeStatus {
            return when (prize.activationStatus) {
                // まだ応募できない
                Prize.ActivationStatus.Inactive -> {
                    Upcoming(prize.startAt)
                }

                // 応募可能
                Prize.ActivationStatus.Active -> {
                    val applicationStatus = prize.applicationStatus

                    when (prize.type) {
                        Prize.Type.Weekly -> {}
                        Prize.Type.Daily, Prize.Type.Welcome -> {
                            if (applicationStatus.isApplied) {
                                return fromApplicationStatus(prize)
                            }
                        }
                    }

                    when {
                        (applicationStatus is Prize.ApplicationStatus.Applied) -> {
                            // 応募済み
                            Applied(
                                appliedAt = applicationStatus.appliedAt,
                                announceAt = prize.announceAt,
                                applicationId = applicationStatus.applicationId,
                            )
                        }

                        // DailyはactiveのままLost/Wonになる
                        (applicationStatus is Prize.ApplicationStatus.Lost) -> {
                            // 落選
                            Lost(
                                appliedAt = applicationStatus.appliedAt,
                                applicationId = applicationStatus.applicationId,
                            )
                        }

                        (applicationStatus is Prize.ApplicationStatus.Won) -> {
                            // 当選
                            Won(
                                appliedAt = applicationStatus.appliedAt,
                                applicationId = applicationStatus.applicationId,
                                receiveExpireAt = applicationStatus.receiveExpireAt,
                            )
                        }

                        prize.hasEntrySeat -> {
                            // 当選枠あり
                            if (!prize.canApply(kyashCoinAmount)) {
                                // コイン不足
                                Shortage(
                                    missingAmount = prize.remainingKyashCoinAmount(kyashCoinAmount),
                                    progress = prize.getPossessionToEntryCoinRatio(kyashCoinAmount),
                                )
                            } else {
                                // 応募可能
                                Applicable(prize.endAt)
                            }
                        }

                        else -> {
                            // 当選枠不足
                            Filled(
                                missingAmount = prize.remainingKyashCoinAmount(kyashCoinAmount),
                                progress = prize.getPossessionToEntryCoinRatio(kyashCoinAmount),
                            )
                        }
                    }
                }

                Prize.ActivationStatus.Ended -> {
                    val applicationStatus = prize.applicationStatus
                    if (applicationStatus is Prize.ApplicationStatus.Applied) {
                        // 応募済み
                        Applied(
                            appliedAt = applicationStatus.appliedAt,
                            announceAt = prize.announceAt,
                            applicationId = applicationStatus.applicationId,
                        )
                    } else {
                        // 懸賞終了
                        Ended
                    }
                }

                Prize.ActivationStatus.Announced -> {
                    fromApplicationStatus(prize)
                }
            }
        }

        fun fromApplicationStatus(prize: Prize): RewardChallengeStatus {
            return when (val applicationStatus = prize.applicationStatus) {
                // 検証終了
                Prize.ApplicationStatus.NotApplied -> Ended

                // 応募済み
                is Prize.ApplicationStatus.Applied -> Applied(
                    appliedAt = applicationStatus.appliedAt,
                    announceAt = prize.announceAt,
                    applicationId = applicationStatus.applicationId,
                )

                // 検証結果確認期限切れ
                is Prize.ApplicationStatus.LotteryResultCheckExpired -> CheckExpired
                is Prize.ApplicationStatus.Lost -> {
                    if (prize.isLotteryResultNotChecked) {
                        // 検証結果未確認
                        ResultAnnounced(
                            applicationStatus.appliedAt,
                            applicationStatus.applicationId,
                            applicationStatus.lotteryResultCheckExpireAt,
                            result = ResultAnnounced.Result.Lost,
                        )
                    } else {
                        // 落選
                        Lost(
                            applicationStatus.appliedAt,
                            applicationStatus.applicationId,
                        )
                    }
                }

                is Prize.ApplicationStatus.Won -> {
                    if (prize.isLotteryResultNotChecked) {
                        // 検証結果未確認
                        ResultAnnounced(
                            applicationStatus.appliedAt,
                            applicationStatus.applicationId,
                            applicationStatus.lotteryResultCheckExpireAt,
                            result = ResultAnnounced.Result.Won,
                        )
                    } else {
                        // 当選
                        Won(
                            applicationStatus.appliedAt,
                            applicationStatus.applicationId,
                            receiveExpireAt = applicationStatus.receiveExpireAt,
                        )
                    }
                }

                // 受け取り期限切れ
                is Prize.ApplicationStatus.ReceiveExpired -> ReceiveExpired

                // 受け取り済み
                is Prize.ApplicationStatus.Received -> Received
            }
        }
    }
}