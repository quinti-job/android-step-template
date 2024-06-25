package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.Prize
import korlibs.time.DateTimeTz
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinPrizeListResponse(
    val nextPage: Int?,
    val coinPrizes: List<CoinPrizeResponse>,
)

@Serializable
data class CoinPrizeResponse(
    val id: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val entryCoinAmount: Long,
    val status: CoinPrizeApplicationStatusResponse,
    val appliedCount: Int,
    val winnersCount: Int,
    val maxWinnersCount: Int,
    val type: CoinPrizeTypeResponse,
    val activationStatus: CoinPrizeActivationStatusResponse,
    val applicationId: String?, // nullable, appliedされると入る
    val lotteryResultChecked: Boolean,
    val giftItem: PrizeResultResponse? = null,
    @Contextual val appliedAt: DateTimeTz?,
    @Contextual val receivedAt: DateTimeTz?,
    @Contextual val startAt: DateTimeTz,
    @Contextual val endAt: DateTimeTz,
    @Contextual val receiveExpireAt: DateTimeTz?,
    @Contextual val announceAt: DateTimeTz,
    @Contextual val lotteryResultCheckExpireAt: DateTimeTz?,
) {

    fun toEntity(): Prize {
        return Prize(
            id = id,
            title = title,
            description = description,
            imageUrl = imageUrl,
            entryCoinAmount = entryCoinAmount,
            applicationStatus = when (status) {
                CoinPrizeApplicationStatusResponse.NotApplied -> Prize.ApplicationStatus.NotApplied
                CoinPrizeApplicationStatusResponse.Applied ->
                    Prize.ApplicationStatus.Applied(
                        appliedAt = appliedAt!!,
                        applicationId = applicationId!!,
                    )

                CoinPrizeApplicationStatusResponse.Lost -> Prize.ApplicationStatus.Lost(
                    appliedAt = appliedAt!!,
                    lotteryResultCheckExpireAt = lotteryResultCheckExpireAt!!,
                    applicationId = applicationId!!,
                )

                CoinPrizeApplicationStatusResponse.Won ->
                    Prize.ApplicationStatus.Won(
                        appliedAt = appliedAt!!,
                        lotteryResultCheckExpireAt = lotteryResultCheckExpireAt!!,
                        receiveExpireAt = receiveExpireAt!!,
                        applicationId = applicationId!!,
                    )

                CoinPrizeApplicationStatusResponse.Received ->
                    Prize.ApplicationStatus.Received(
                        appliedAt = appliedAt!!,
                        receivedAt = receivedAt!!,
                        applicationId = applicationId!!,
                    )

                CoinPrizeApplicationStatusResponse.ReceiveExpired ->
                    Prize.ApplicationStatus.ReceiveExpired(appliedAt = appliedAt!!)

                CoinPrizeApplicationStatusResponse.LotteryResultCheckExpired ->
                    Prize.ApplicationStatus.LotteryResultCheckExpired(appliedAt = appliedAt!!)
            },
            appliedCount = appliedCount,
            winnersCount = winnersCount,
            maxWinnersCount = maxWinnersCount,
            startAt = startAt,
            endAt = endAt,
            type = type.toEntity(),
            announceAt = announceAt,
            activationStatus = activationStatus.toEntity(),
            lotteryResultChecked = lotteryResultChecked,
            giftType = giftItem?.toEntity() ?: Prize.GiftType.Redirect,
        )
    }
}

@Serializable
enum class CoinPrizeApplicationStatusResponse {
    // not_applied (申込前)、applied (申込完了)、lost (落選)、won (当選して受け取っていない)、received (当選して受け取っている)
    @SerialName("not_applied")
    NotApplied,

    @SerialName("applied")
    Applied,

    @SerialName("lost")
    Lost,

    @SerialName("won")
    Won,

    @SerialName("received")
    Received,

    @SerialName("receive_expired")
    ReceiveExpired,

    @SerialName("check_expired")
    LotteryResultCheckExpired,

    ;
}

@Serializable
enum class CoinPrizeTypeResponse {
    @SerialName("weekly")
    Weekly,

    @SerialName("daily")
    Daily,

    @SerialName("welcome")
    Welcome,

    ;

    fun toEntity(): Prize.Type {
        return when (this) {
            Weekly -> Prize.Type.Weekly
            Daily -> Prize.Type.Daily
            Welcome -> Prize.Type.Welcome
        }
    }
}

@Serializable
enum class CoinPrizeActivationStatusResponse {
    @SerialName("active")
    Active,

    @SerialName("inactive")
    Inactive,

    @SerialName("ended")
    Ended,

    @SerialName("announced")
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
}

@Serializable
data class PrizeResultResponse(
    val vendorKey: String,
    val receiptMethod: String,
    val amount: Long? = null,
) {

    fun toEntity(): Prize.GiftType {
        return when (receiptMethod) {
            "auto" -> when (vendorKey) {
                "kyash_point" -> Prize.GiftType.Auto.Point(amount ?: 0)
                else -> throw IllegalArgumentException("Unknown vendorType: $vendorKey")
            }
            else -> Prize.GiftType.Redirect
        }
    }
}