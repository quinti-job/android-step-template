package com.quinti.android_step_template.kmp.data.repository

import co.kyash.mobile.model.reward.DailyRouletteOptions
import co.kyash.mobile.model.reward.DailyRouletteStatus
import co.kyash.mobile.model.reward.KyashCoin
import co.kyash.mobile.model.reward.KyashCoinHistoryList
import co.kyash.mobile.model.reward.StampCard
import co.kyash.mobile.model.reward.StampCardExchangeResult
import co.kyash.mobile.model.reward.StampCardList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

@Suppress("TooManyFunctions")
interface KyashCoinRepository {

    val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit>
    val isShownRewardTabCoachMarkFlow: Flow<Boolean>

    suspend fun requestUpdateRewardTabIndicator()

    suspend fun hideRewardTabCoachMark()

    suspend fun getRewardOnboardingShown(): Boolean

    suspend fun getCurrentCoinAmount(): KyashCoin

    suspend fun getDailyRouletteStatus(): DailyRouletteStatus

    suspend fun getDailyRouletteOptions(): DailyRouletteOptions

    suspend fun getDailyRouletteResult(): Long

    suspend fun getRewardTopOnboardingShown(): Boolean

    suspend fun setRewardTopOnboardingShown()

    suspend fun sendRewardTopOnboardingShowEvent()

    suspend fun getRewardTabNewBalloonShown(): Boolean

    suspend fun setRewardTabNewBalloonShown()

    suspend fun isNeedShowRewardStampOnboarding(): Boolean

    suspend fun setNeedShowRewardStampOnboarding(needShow: Boolean)

    suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int = 20,
    ): KyashCoinHistoryList

    suspend fun getStampCardList(
        lastCreatedAt: String?,
        limit: Int = 1,
    ): StampCardList

    suspend fun getCurrentStampCard(): StampCard?

    suspend fun exchangeStampReward(id: String): StampCardExchangeResult
}