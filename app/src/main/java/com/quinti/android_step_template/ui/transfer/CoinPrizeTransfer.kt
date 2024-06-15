package com.quinti.android_step_template.ui.transfer

import android.os.Parcelable
import co.kyash.mobile.model.reward.Prize
import korlibs.time.DateTimeTz
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinPrizeTransfer(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val entryCoinAmount: Long,
    val status: PrizeApplicationStatusTransfer,
    val appliedCount: Int,
    val maxWinnersCount: Int,
    val startAt: DateTimeTz,
    val activationStatus: ActivationStatusTransfer,
    val prizeType: PrizeTypeTransfer,
    val endAt: DateTimeTz,
    val announceAt: DateTimeTz,
    val winnersCount: Int,
    val lotteryResultChecked: Boolean,
    val giftType: PrizeGiftTypeTransfer,
) : Parcelable {

    fun toEntity(): Prize {
        return Prize(
            id = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
            entryCoinAmount = entryCoinAmount,
            applicationStatus = status.toEntity(),
            appliedCount = appliedCount,
            maxWinnersCount = maxWinnersCount,
            startAt = startAt,
            activationStatus = activationStatus.toEntity(),
            type = prizeType.toEntity(),
            endAt = endAt,
            winnersCount = winnersCount,
            announceAt = announceAt,
            lotteryResultChecked = lotteryResultChecked,
            giftType = giftType.toEntity(),
        )
    }

    companion object {
        fun from(prize: Prize): CoinPrizeTransfer {
            return CoinPrizeTransfer(
                id = prize.id,
                title = prize.title,
                description = prize.description,
                imageUrl = prize.imageUrl,
                entryCoinAmount = prize.entryCoinAmount,
                status = PrizeApplicationStatusTransfer.from(prize.applicationStatus),
                appliedCount = prize.appliedCount,
                maxWinnersCount = prize.maxWinnersCount,
                startAt = prize.startAt,
                prizeType = PrizeTypeTransfer.from(prize.type),
                activationStatus = ActivationStatusTransfer.from(prize.activationStatus),
                endAt = prize.endAt,
                announceAt = prize.announceAt,
                winnersCount = prize.winnersCount,
                lotteryResultChecked = prize.lotteryResultChecked,
                giftType = PrizeGiftTypeTransfer.from(prize.giftType),
            )
        }
    }

    @Parcelize
    enum class ActivationStatusTransfer : Parcelable {
        Active,
        Inactive,
        Ended,
        Announced,

        ;

        fun toEntity(): Prize.ActivationStatus {
            return when (this) {
                Active -> Prize.ActivationStatus.Active
                Inactive -> Prize.ActivationStatus.Inactive
                Ended -> Prize.ActivationStatus.Ended
                Announced -> Prize.ActivationStatus.Announced
            }
        }

        companion object {
            fun from(status: Prize.ActivationStatus): ActivationStatusTransfer {
                return when (status) {
                    Prize.ActivationStatus.Active -> Active
                    Prize.ActivationStatus.Inactive -> Inactive
                    Prize.ActivationStatus.Ended -> Ended
                    Prize.ActivationStatus.Announced -> Announced
                }
            }
        }
    }

    @Parcelize
    sealed class PrizeTypeTransfer : Parcelable {
        @Parcelize
        data object Weekly : PrizeTypeTransfer()

        @Parcelize
        data object Daily : PrizeTypeTransfer()

        data object Welcome : PrizeTypeTransfer()

        fun toEntity(): Prize.Type {
            return when (this) {
                Daily -> Prize.Type.Daily
                Weekly -> Prize.Type.Weekly
                Welcome -> Prize.Type.Welcome
            }
        }

        companion object {
            fun from(type: Prize.Type): PrizeTypeTransfer {
                return when (type) {
                    Prize.Type.Daily -> Daily
                    Prize.Type.Weekly -> Weekly
                    Prize.Type.Welcome -> Welcome
                }
            }
        }
    }

    @Parcelize
    sealed class PrizeGiftTypeTransfer : Parcelable {
        @Parcelize
        data object Sending : PrizeGiftTypeTransfer()

        @Parcelize
        sealed class Received : PrizeGiftTypeTransfer() {
            data class Point(val amount: Long) : Received()
        }

        fun toEntity(): Prize.GiftType {
            return when (this) {
                Sending -> Prize.GiftType.Redirect
                is Received.Point -> Prize.GiftType.Auto.Point(amount)
            }
        }

        companion object {
            fun from(type: Prize.GiftType): PrizeGiftTypeTransfer {
                return when (type) {
                    is Prize.GiftType.Redirect -> Sending
                    is Prize.GiftType.Auto.Point -> Received.Point(type.amount)
                }
            }
        }
    }

    @Parcelize
    sealed class PrizeApplicationStatusTransfer : Parcelable {
        @Parcelize
        data object NotApplied : PrizeApplicationStatusTransfer()

        @Parcelize
        data class Applied(
            val appliedAt: DateTimeTz,
            val applicationId: String,
        ) : PrizeApplicationStatusTransfer()

        @Parcelize
        data class Lost(
            val appliedAt: DateTimeTz,
            val lotteryResultCheckExpireAt: DateTimeTz,
        ) : PrizeApplicationStatusTransfer()

        @Parcelize
        data class Won(
            val appliedAt: DateTimeTz,
            val receiveExpireAt: DateTimeTz,
            val lotteryResultCheckExpireAt: DateTimeTz,
            val applicationId: String,
        ) : PrizeApplicationStatusTransfer()

        @Parcelize
        data class Received(
            val appliedAt: DateTimeTz,
            val receivedAt: DateTimeTz,
            val applicationId: String,
        ) : PrizeApplicationStatusTransfer()

        @Parcelize
        data class ReceiveExpired(
            val appliedAt: DateTimeTz,
        ) : PrizeApplicationStatusTransfer()

        @Parcelize
        data class LotteryResultCheckExpired(
            val appliedAt: DateTimeTz,
        ) : PrizeApplicationStatusTransfer()

        fun toEntity(): Prize.ApplicationStatus {
            return when (this) {
                is Applied -> Prize.ApplicationStatus.Applied(
                    appliedAt = this.appliedAt,
                    applicationId = this.applicationId,
                )

                is Lost -> Prize.ApplicationStatus.Lost(
                    appliedAt = appliedAt,
                    lotteryResultCheckExpireAt = lotteryResultCheckExpireAt,
                    applicationId = "",
                )

                NotApplied -> Prize.ApplicationStatus.NotApplied
                is Received -> Prize.ApplicationStatus.Received(
                    appliedAt = appliedAt,
                    receivedAt = this.receivedAt,
                    applicationId = this.applicationId,
                )

                is Won -> Prize.ApplicationStatus.Won(
                    appliedAt = appliedAt,
                    receiveExpireAt = this.receiveExpireAt,
                    lotteryResultCheckExpireAt = this.lotteryResultCheckExpireAt,
                    applicationId = this.applicationId,
                )

                is LotteryResultCheckExpired -> Prize.ApplicationStatus.LotteryResultCheckExpired(
                    appliedAt = appliedAt,
                )

                is ReceiveExpired -> Prize.ApplicationStatus.ReceiveExpired(appliedAt = appliedAt)
            }
        }

        companion object {
            fun from(status: Prize.ApplicationStatus): PrizeApplicationStatusTransfer {
                return when (status) {
                    is Prize.ApplicationStatus.Applied -> Applied(
                        appliedAt = status.appliedAt,
                        applicationId = status.applicationId,
                    )

                    is Prize.ApplicationStatus.Lost -> Lost(
                        appliedAt = status.appliedAt,
                        lotteryResultCheckExpireAt = status.lotteryResultCheckExpireAt,
                    )

                    Prize.ApplicationStatus.NotApplied -> NotApplied
                    is Prize.ApplicationStatus.Received -> Received(
                        appliedAt = status.appliedAt,
                        receivedAt = status.receivedAt,
                        applicationId = status.applicationId,
                    )

                    is Prize.ApplicationStatus.Won -> Won(
                        appliedAt = status.appliedAt,
                        receiveExpireAt = status.receiveExpireAt,
                        applicationId = status.applicationId,
                        lotteryResultCheckExpireAt = status.lotteryResultCheckExpireAt,
                    )

                    is Prize.ApplicationStatus.LotteryResultCheckExpired ->
                        LotteryResultCheckExpired(
                            appliedAt = status.appliedAt,
                        )

                    is Prize.ApplicationStatus.ReceiveExpired -> ReceiveExpired(
                        appliedAt = status.appliedAt,
                    )
                }
            }
        }
    }
}