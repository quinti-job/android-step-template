package com.quinti.android_step_template.kmp.domain.test

import app.cash.turbine.test
import co.kyash.mobile.data.repository.KyashCoinRepository
import co.kyash.mobile.data.repository.PrizeRepository
import co.kyash.mobile.model.reward.ApplyCoinPrizeResult
import co.kyash.mobile.model.reward.DailyRouletteOptions
import co.kyash.mobile.model.reward.DailyRouletteStatus
import co.kyash.mobile.model.reward.KyashCoin
import co.kyash.mobile.model.reward.KyashCoinHistoryList
import co.kyash.mobile.model.reward.Prize
import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.model.reward.ReceivePrizeType
import co.kyash.mobile.model.reward.StampCard
import co.kyash.mobile.model.reward.StampCardList
import co.kyash.mobile.model.reward.UncheckedPrizeSummary
import com.quinti.android_step_template.kmp.data.entity.StampCardList
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.test.runTest

class WatchNeedShowRewardTabIndicatorTest {

    // Given: ルーレットが利用可能で、未確認の懸賞当落結果がない
    // When: リワードタブのインジケーターを表示するかどうかのリクエストがあった
    // Then: リワードタブのインジケーターを表示する
    @Test
    fun givenRouletteAvailableAndUncheckedPrizeCountIsZero_whenRequest_thenShowIndicator() =
        runTest {
            val useCase = createUseCase(
                rouletteAvailable = { true },
                uncheckedCount = { 0 },
            )

            val resultFlow = useCase()
            resultFlow.test {
                assertEquals(true, awaitItem())
            }
        }

    // Given: ルーレットが利用済みで、未確認の懸賞当落結果がある
    // When: リワードタブのインジケーターを表示するかどうかのリクエストがあった
    // Then: リワードタブのインジケーターを表示する
    @Test
    fun givenRouletteDoneAndUncheckedPrizeCountIsNotZero_whenRequest_thenShowIndicator() =
        runTest {
            val useCase = createUseCase(
                rouletteAvailable = { false },
                uncheckedCount = { 1 },
            )

            val resultFlow = useCase()
            resultFlow.test {
                assertEquals(true, awaitItem())
            }
        }

    // Given: ルーレットが利用済みで、未確認の懸賞当落結果がない
    // When: リワードタブのインジケーターを表示するかどうかのリクエストがあった
    // Then: リワードタブのインジケーターを表示する
    @Test
    fun givenRouletteDoneAndUncheckedPrizeCountIsZero_whenRequest_thenNotShowIndicator() =
        runTest {
            val useCase = createUseCase(
                rouletteAvailable = { false },
                uncheckedCount = { 0 },
            )

            val resultFlow = useCase()
            resultFlow.test {
                assertEquals(false, awaitItem())
            }
        }

    // Given: 3回連続でリクエストがあった（0, 100L, 1001L)
    // When: リワードタブのインジケーターを表示するかどうかのリクエストがあった
    // Then: リワードタブのインジケーターが表示されるのは2回目のみ
    // 1回目：true, 2回目: true, 3回目: false, 4回目：trueになるが、2回目はスキップされて、初回と合わせて全部で3回流れる
    @Test
    fun givenRequestThreeTimes_whenRequest_thenShowIndicatorOnlySecondTime() =
        runTest {
            val flow = MutableSharedFlow<Unit>()
            var count = 0
            val useCase = createUseCase(
                flow = flow,
                rouletteAvailable = {
                    when (count) {
                        0 -> true
                        1 -> false
                        2 -> false
                        else -> true
                    }
                },
                uncheckedCount = {
                    when (count) {
                        0 -> 0
                        1 -> 1
                        2 -> 0
                        else -> 1
                    }
                },
            )

            val resultFlow = useCase()
            resultFlow.test {
                delay(101L)
                count++
                flow.emit(Unit)
                delay(30L)
                count++
                flow.emit(Unit)
                delay(101L)
                count++
                flow.emit(Unit)
                assertEquals(true, awaitItem())
                assertEquals(false, awaitItem())
                assertEquals(true, awaitItem())
            }
        }

    // Given: ルーレットが利用状況取得でエラーが発生
    // When: リワードタブのインジケーターを表示するかどうかのリクエストがあった
    // Then: リワードタブのインジケーターを表示しない
    @Test
    fun givenRouletteStatusError_whenRequest_thenNotShowIndicator() =
        runTest {
            val useCase = createUseCase(
                rouletteAvailable = { throw Exception() },
                uncheckedCount = { 0 },
            )

            val resultFlow = useCase()
            resultFlow.test {
                assertEquals(false, awaitItem())
            }
        }

    private fun createUseCase(
        flow: SharedFlow<Unit> = MutableStateFlow(Unit),
        uncheckedCount: () -> Int = { 0 },
        rouletteAvailable: () -> Boolean = { false },
    ): WatchNeedShowRewardTabIndicator {
        return WatchNeedShowRewardTabIndicator(
            kyashCoinRepository = KyashCoinRepositoryTestImpl(
                flow = flow,
                rouletteAvailable = rouletteAvailable,
            ),
            prizeRepository = PrizeRepositoryTestImpl(uncheckedCount),
            debounceTimeMillis = 100L,
        )
    }
}

private class PrizeRepositoryTestImpl(val count: () -> Int) : PrizeRepository {

    override suspend fun getUncheckedPrizes(): UncheckedPrizeSummary? {
        return UncheckedPrizeSummary(
            count = count(),
            prize = UncheckedPrizeSummary.PrizePreview("", "", "", 1000L),
        )
    }

    override val activeWeeklyPrizes: Flow<PrizeList>
        get() = TODO("Not yet implemented")
    override val activeDailyPrizes: Flow<PrizeList>
        get() = TODO("Not yet implemented")
    override val upcomingWeeklyPrizes: Flow<PrizeList>
        get() = TODO("Not yet implemented")
    override val appliedWeeklyPrizes: Flow<PrizeList>
        get() = TODO("Not yet implemented")
    override val appliedDailyPrizes: Flow<PrizeList>
        get() = TODO("Not yet implemented")

    override suspend fun loadActiveWeeklyPrizes(refresh: Boolean, limit: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun loadActiveDailyPrizes(refresh: Boolean, limit: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun loadUpcomingWeeklyPrizes(refresh: Boolean, limit: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun loadAppliedWeeklyPrizes(refresh: Boolean, limit: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun loadAppliedDailyPrizes(refresh: Boolean, limit: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getPrize(prizeId: String): Prize {
        TODO("Not yet implemented")
    }

    override suspend fun applyPrize(prizeId: String): ApplyCoinPrizeResult {
        TODO("Not yet implemented")
    }

    override suspend fun sharedToSns(prizeId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun receivePrize(applicationId: String): ReceivePrizeType {
        TODO("Not yet implemented")
    }

    override suspend fun checkLotteryResult(prizeId: String) {
        TODO("Not yet implemented")
    }
}

private class KyashCoinRepositoryTestImpl(
    private val rouletteAvailable: () -> Boolean,
    flow: SharedFlow<Unit>,
) : KyashCoinRepository {
    override val requestUpdateRewardTabIndicatorFlow: SharedFlow<Unit> = flow
        .onSubscription { emit(Unit) }

    override suspend fun getDailyRouletteStatus(): DailyRouletteStatus {
        return when (rouletteAvailable()) {
            true -> DailyRouletteStatus.Available
            false -> DailyRouletteStatus.Done
        }
    }

    override suspend fun requestUpdateRewardTabIndicator() {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentCoinAmount(): KyashCoin {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyRouletteOptions(): DailyRouletteOptions {
        TODO("Not yet implemented")
    }

    override suspend fun getDailyRouletteResult(): Long {
        TODO("Not yet implemented")
    }

    override suspend fun getRewardTopOnboardingShown(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setRewardTopOnboardingShown() {
        TODO("Not yet implemented")
    }

    override suspend fun getRewardTabNewBalloonShown(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun setRewardTabNewBalloonShown() {
        TODO("Not yet implemented")
    }

    override suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int,
    ): KyashCoinHistoryList {
        TODO("Not yet implemented")
    }

    override suspend fun getStampCardList(lastCreatedAt: String?, limit: Int): StampCardList {
        TODO("Not yet implemented")
    }

    override suspend fun getCurrentStampCard(): StampCard? {
        TODO("Not yet implemented")
    }
}
