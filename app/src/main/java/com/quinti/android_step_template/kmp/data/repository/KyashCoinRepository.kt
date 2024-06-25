package com.quinti.android_step_template.kmp.data.repository

import com.quinti.android_step_template.kmp.data.api.KyashCoinApi
import com.quinti.android_step_template.kmp.data.datasource.RewardLocalDataSource
import com.quinti.android_step_template.kmp.data.entity.DailyRouletteOptions
import com.quinti.android_step_template.kmp.data.entity.DailyRouletteStatus
import com.quinti.android_step_template.kmp.data.entity.KyashCoin
import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistoryList
import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.entity.StampCardList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map

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

@Suppress("TooManyFunctions")
class KyashCoinRepositoryImpl(
    private val kyashCoinApi: KyashCoinApi,
    private val localDataSource: RewardLocalDataSource,
) : KyashCoinRepository {

    override val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit> =
        localDataSource.requestUpdateRewardTabIndicatorFlow

    override val isShownRewardTabCoachMarkFlow: Flow<Boolean> =
        localDataSource.isShownRewardTabCoachMarkFlow.map {
            // リワードオンボーディング202404が有効な場合のみ表示する
            // リリースフラグ削除時はこの map ごと削除する。
            it
        }

    override suspend fun requestUpdateRewardTabIndicator() {
        localDataSource.requestUpdateRewardTabIndicator()
    }

    override suspend fun hideRewardTabCoachMark() {
        localDataSource.setRewardTabCoachMarkShown()
    }

    override suspend fun getCurrentCoinAmount(): KyashCoin {
        val result = kyashCoinApi.getCurrentKyashCoin()
        return result.result.data.let {
            KyashCoin(
                availableAmount = it.availableAmount,
                pendingAmount = it.pendingAmount,
                recentExpiry = it.recentExpiry?.let { recentExpiry ->
                    KyashCoin.Expiry(
                        amount = recentExpiry.amount,
                        expireAt = recentExpiry.expireAt,
                    )
                },
            )
        }
    }

    override suspend fun getDailyRouletteStatus(): DailyRouletteStatus {
        return kyashCoinApi.getDailyRouletteStatus().result.data.let {
            // まだルーレットを非表示にするステータスは返してもらっていないので実装されたらUnavailableを設定する
            if (it.available) {
                DailyRouletteStatus.Available
            } else {
                DailyRouletteStatus.Done
            }
        }
    }

    override suspend fun getDailyRouletteOptions(): DailyRouletteOptions {
        return kyashCoinApi.getDailyRouletteOptions().result.data.toEntity()
    }

    override suspend fun getDailyRouletteResult(): Long {
        return kyashCoinApi.getDailyRouletteResult().result.data.winningAmount
    }

    override suspend fun getRewardTopOnboardingShown(): Boolean {
        return localDataSource.isRewardTopOnboardingShown()
    }

    override suspend fun setRewardTopOnboardingShown() {
        localDataSource.setRewardTopOnboardingShown(true)
    }

    override suspend fun sendRewardTopOnboardingShowEvent() {
        kyashCoinApi.postRewardOnboardingEvent()
    }

    override suspend fun getRewardTabNewBalloonShown(): Boolean {
        return localDataSource.isRewardTabBalloonShown()
    }

    override suspend fun setRewardTabNewBalloonShown() {
        return localDataSource.setRewardTabBalloonShown(true)
    }

    override suspend fun isNeedShowRewardStampOnboarding(): Boolean {
        return localDataSource.isNeedShowRewardStampOnboarding()
    }

    override suspend fun setNeedShowRewardStampOnboarding(needShow: Boolean) {
        return localDataSource.setNeedShowRewardStampOnboarding(needShow)
    }

    override suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int,
    ): KyashCoinHistoryList {
        return kyashCoinApi.getKyashCoinHistory(
            lastCreatedAt = lastCreatedAt,
            limit = limit,
        ).result.data.toEntity()
    }

    override suspend fun getStampCardList(
        lastCreatedAt: String?,
        limit: Int,
    ): StampCardList {
        return kyashCoinApi.getStampCardList(
            lastCreatedAt = lastCreatedAt,
            limit = limit,
        ).result.data.toEntity()
    }

    override suspend fun getCurrentStampCard(): StampCard? {
        return kyashCoinApi.getStampCardList(
            lastCreatedAt = null,
            limit = 1,
        ).result.data.toEntity().stampcards.firstOrNull()
    }

    override suspend fun exchangeStampReward(id: String): StampCardExchangeResult {
        return kyashCoinApi.exchangeStampCardReward(id).result.data.toEntity()
    }

    override suspend fun getRewardOnboardingShown(): Boolean {
        // リワードオンボーディング202404が無効の場合は常に表示済みとする
        return kyashCoinApi.getRewardOnboardingEvent().result.data.isOnboardingStarted
    }
}