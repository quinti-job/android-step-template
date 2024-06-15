package com.quinti.android_step_template.kmp.data.repository

import co.kyash.mobile.model.reward.ApplyCoinPrizeResult
import co.kyash.mobile.model.reward.Prize
import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.model.reward.ReceivePrizeType
import co.kyash.mobile.model.reward.UncheckedPrizeSummary
import kotlinx.coroutines.flow.Flow

@Suppress("TooManyFunctions")
interface PrizeRepository {

    val activeWeeklyPrizes: Flow<PrizeList>
    val activeDailyPrizes: Flow<PrizeList>
    val upcomingWeeklyPrizes: Flow<PrizeList>
    val appliedWeeklyPrizes: Flow<PrizeList>
    val appliedDailyPrizes: Flow<PrizeList>
    val welcomePrizes: Flow<PrizeList>

    suspend fun loadActiveWeeklyPrizes(refresh: Boolean, limit: Int = 20)
    suspend fun loadActiveDailyPrizes(refresh: Boolean, limit: Int = 20)
    suspend fun loadUpcomingWeeklyPrizes(refresh: Boolean, limit: Int = 20)
    suspend fun loadAppliedWeeklyPrizes(refresh: Boolean, limit: Int = 20)
    suspend fun loadAppliedDailyPrizes(refresh: Boolean, limit: Int = 20)
    suspend fun loadWelcomePrizes(refresh: Boolean, limit: Int = 20)
    suspend fun getPrize(prizeId: String): Prize
    suspend fun getWelcomeChallenge(): Prize?

    /**
     * @throws RewardApplicationException 応募に失敗した場合に投げられる
     * @throws ApiErrorResponseException その他のエラー
     */
    suspend fun applyPrize(prizeId: String): ApplyCoinPrizeResult
    suspend fun sharedToSns(prizeId: String)
    suspend fun receivePrize(applicationId: String): ReceivePrizeType
    suspend fun getUncheckedPrizes(): UncheckedPrizeSummary?
    suspend fun checkLotteryResult(prizeId: String)

    enum class PrizeTypeFilter(val value: String) {
        Daily("daily"),
        Weekly("weekly"),
        Welcome("welcome"),

        ;
    }

    enum class PrizeStatusFilter(val value: String) {
        Active("active"),
        Upcoming("upcoming"),
    }
}