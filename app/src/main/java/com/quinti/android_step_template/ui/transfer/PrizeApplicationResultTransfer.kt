package com.quinti.android_step_template.ui.transfer

import android.os.Parcelable
import co.kyash.mobile.model.reward.Prize
import co.kyash.mobile.reward.PrizeApplicationResult
import com.quinti.android_step_template.kmp.data.entity.Prize
import korlibs.time.DateTimeTz
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class PrizeApplicationResultTransfer : Parcelable {
    @Parcelize
    data class Won(
        val prizeId: String,
        val title: String,
        val imageUrl: String,
        val entryCoinAmount: Long,
        val type: Prize.Type,
        val receiveExpireAt: DateTimeTz,
        val applicationId: String,
        val isChecked: Boolean,
        val prizeGiftType: PrizeGiftTypeTransfer,
    ) : PrizeApplicationResultTransfer()

    @Parcelize
    data class Lost(
        val prizeId: String,
        val title: String,
        val imageUrl: String,
        val entryCoinAmount: Long,
        val type: Prize.Type,
        val isChecked: Boolean,
        val applicationId: String,
    ) : PrizeApplicationResultTransfer() {
        @Parcelize
        sealed class AppliedParticipationRewardTransfer : Parcelable {
            @Parcelize
            data object None : AppliedParticipationRewardTransfer()

            @Parcelize
            sealed class Available : AppliedParticipationRewardTransfer() {
                @Parcelize
                data class KyashCoin(
                    val amount: Long,
                ) : Available()
            }
        }
    }

    @Parcelize
    data class Pending(
        val prizeId: String,
        val title: String,
        val imageUrl: String,
        val entryCoinAmount: Long,
        val type: Prize.Type,
        val applicationId: String,
    ) : PrizeApplicationResultTransfer()

    fun toEntity(): PrizeApplicationResult {
        return when (this) {
            is Won -> PrizeApplicationResult.Won(
                prizeId = prizeId,
                title = title,
                imageUrl = imageUrl,
                entryCoinAmount = entryCoinAmount,
                type = type,
                receiveExpireAt = receiveExpireAt,
                applicationId = applicationId,
                isChecked = isChecked,
                giftType = prizeGiftType.toEntity(),
            )

            is Lost -> PrizeApplicationResult.Lost(
                prizeId = prizeId,
                title = title,
                imageUrl = imageUrl,
                entryCoinAmount = entryCoinAmount,
                type = type,
                isChecked = isChecked,
                applicationId = applicationId,
            )

            is Pending -> PrizeApplicationResult.Pending(
                prizeId = prizeId,
                title = title,
                imageUrl = imageUrl,
                entryCoinAmount = entryCoinAmount,
                type = type,
                applicationId = applicationId,
            )
        }
    }

    companion object {
        fun from(from: PrizeApplicationResult): PrizeApplicationResultTransfer {
            return when (from) {
                is PrizeApplicationResult.Won -> Won(
                    prizeId = from.prizeId,
                    title = from.title,
                    imageUrl = from.imageUrl,
                    entryCoinAmount = from.entryCoinAmount,
                    type = from.type,
                    receiveExpireAt = from.receiveExpireAt,
                    applicationId = from.applicationId,
                    isChecked = from.isChecked,
                    prizeGiftType = PrizeGiftTypeTransfer.from(from.giftType),
                )

                is PrizeApplicationResult.Lost -> Lost(
                    prizeId = from.prizeId,
                    title = from.title,
                    imageUrl = from.imageUrl,
                    entryCoinAmount = from.entryCoinAmount,
                    type = from.type,
                    isChecked = from.isChecked,
                    applicationId = from.applicationId,
                )

                is PrizeApplicationResult.Pending -> Pending(
                    prizeId = from.prizeId,
                    title = from.title,
                    imageUrl = from.imageUrl,
                    entryCoinAmount = from.entryCoinAmount,
                    type = from.type,
                    applicationId = from.applicationId,
                )
            }
        }
    }
}

@Parcelize
sealed class PrizeGiftTypeTransfer : Parcelable {

    fun toEntity() = when (this) {
        is Sending -> Prize.GiftType.Redirect
        is Auto.Point -> Prize.GiftType.Auto.Point(amount)
    }

    @Parcelize
    data object Sending : PrizeGiftTypeTransfer()

    @Parcelize
    sealed class Auto : PrizeGiftTypeTransfer() {
        @Parcelize
        data class Point(val amount: Long) : Auto()
    }

    companion object {
        fun from(from: Prize.GiftType): PrizeGiftTypeTransfer {
            return when (from) {
                is Prize.GiftType.Redirect -> Sending
                is Prize.GiftType.Auto.Point -> Auto.Point(from.amount)
            }
        }
    }
}
