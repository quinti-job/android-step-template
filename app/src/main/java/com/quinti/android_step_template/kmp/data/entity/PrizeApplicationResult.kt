package com.quinti.android_step_template.kmp.data.entity

import korlibs.time.DateTimeTz

sealed class PrizeApplicationResult {
    abstract val title: String
    abstract val imageUrl: String
    abstract val prizeId: String
    abstract val type: Prize.Type
    abstract val isChecked: Boolean
    abstract val applicationId: String
    abstract val entryCoinAmount: Long

    data class Won(
        override val prizeId: String,
        override val applicationId: String,
        override val title: String,
        override val imageUrl: String,
        override val entryCoinAmount: Long,
        override val type: Prize.Type,
        val receiveExpireAt: DateTimeTz,
        override val isChecked: Boolean,
        val giftType: Prize.GiftType,
    ) : PrizeApplicationResult()

    data class Lost(
        override val prizeId: String,
        override val title: String,
        override val imageUrl: String,
        override val applicationId: String,
        override val entryCoinAmount: Long,
        override val type: Prize.Type,
        override val isChecked: Boolean,
    ) : PrizeApplicationResult()

    data class Pending(
        override val prizeId: String,
        override val applicationId: String,
        override val title: String,
        override val imageUrl: String,
        override val entryCoinAmount: Long,
        override val type: Prize.Type,
        override val isChecked: Boolean = false,
    ) : PrizeApplicationResult()

    companion object {
        fun getOrNull(
            prize: Prize,
        ): PrizeApplicationResult? {
            return when (val status = prize.applicationStatus) {
                is Prize.ApplicationStatus.Lost ->
                    Lost(
                        prizeId = prize.id,
                        title = prize.title,
                        imageUrl = prize.imageUrl,
                        entryCoinAmount = prize.entryCoinAmount,
                        type = prize.type,
                        isChecked = prize.lotteryResultChecked,
                        applicationId = status.applicationId,
                    )

                is Prize.ApplicationStatus.Won ->
                    Won(
                        prizeId = prize.id,
                        title = prize.title,
                        imageUrl = prize.imageUrl,
                        entryCoinAmount = prize.entryCoinAmount,
                        type = prize.type,
                        receiveExpireAt = status.receiveExpireAt,
                        applicationId = status.applicationId,
                        isChecked = prize.lotteryResultChecked,
                        giftType = prize.giftType,
                    )

                is Prize.ApplicationStatus.Applied ->
                    Pending(
                        prizeId = prize.id,
                        title = prize.title,
                        imageUrl = prize.imageUrl,
                        entryCoinAmount = prize.entryCoinAmount,
                        type = prize.type,
                        applicationId = status.applicationId,
                    )

                else -> null
            }
        }
    }
}