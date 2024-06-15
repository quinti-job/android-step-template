package com.quinti.android_step_template.kmp.domain.usecase.test

@file:OptIn(ExperimentalCoroutinesApi::class)

import app.cash.turbine.test
import co.kyash.mobile.data.api.KyashCoinApi
import co.kyash.mobile.data.api.response.Response
import co.kyash.mobile.data.api.response.coin.ApplyCoinPrizeResponse
import co.kyash.mobile.data.api.response.coin.CoinPrizeActivationStatusResponse
import co.kyash.mobile.data.api.response.coin.CoinPrizeApplicationStatusResponse
import co.kyash.mobile.data.api.response.coin.CoinPrizeListResponse
import co.kyash.mobile.data.api.response.coin.CoinPrizeResponse
import co.kyash.mobile.data.api.response.coin.CoinPrizeTypeResponse
import co.kyash.mobile.data.api.response.coin.CurrentKyashCoinResponse
import co.kyash.mobile.data.api.response.coin.DailyRouletteOptionsResponse
import co.kyash.mobile.data.api.response.coin.DailyRouletteResultResponse
import co.kyash.mobile.data.api.response.coin.DailyRouletteStatusResponse
import co.kyash.mobile.data.api.response.coin.KyashCoinHistoryListResponse
import co.kyash.mobile.data.api.response.coin.ReceivePrizeResponse
import co.kyash.mobile.data.api.response.coin.StampCardListResponse
import co.kyash.mobile.data.api.response.coin.UncheckedCoinPrizeResponse
import co.kyash.mobile.data.datasource.CoinPrizeInMemoryDataSource
import co.kyash.mobile.data.datasource.CoinPrizeLocalDataSource
import co.kyash.mobile.data.datasource.CoinPrizePageInMemoryDataSource
import co.kyash.mobile.data.datasource.CoinPrizePageLocalDataSource
import co.kyash.mobile.fixture.fixture
import co.kyash.mobile.model.reward.Prize
import korlibs.time.DateTimeTz
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest

class PrizeRepositoryTest {
    private lateinit var api: KyashCoinApi
    private lateinit var prizeLocalDataSource: CoinPrizeLocalDataSource
    private lateinit var prizePageLocalDataSource: CoinPrizePageLocalDataSource

    @BeforeTest
    fun setUp() {
        api = KyashCoinApiMock().apply {
            appliedMock = { _, page, _ ->
                CoinPrizeListResponse(
                    nextPage = page?.plus(1),
                    coinPrizes = listOf(
                        dummyPrizeResponse.copy(id = "$page-1"),
                        dummyPrizeResponse.copy(id = "$page-2"),
                    ),
                )
            }
        }
        prizeLocalDataSource = CoinPrizeInMemoryDataSource()
        prizePageLocalDataSource = CoinPrizePageInMemoryDataSource()
    }

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageがnullの場合、１ページ目を取得すること。
    // Given: refresh = false, savedNextPage = null, apiNextPage = 2
    // When: loadActiveWeeklyPrizes
    // Then: emit activeWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }

            // When
            val repository = createRepository()
            repository.loadActiveWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.activeWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("1-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("1-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageが存在する場合、そのページを取得すること。
    // Given: refresh = false, savedNextPage = 3, apiNextPage = 4
    // When: loadActiveWeeklyPrizes
    // Then: emit activeWeeklyPrizes = [3-1, 3-2], hasNext = true
    @Test
    fun givenSecondResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForSecondPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = 4,
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateActiveWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadActiveWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.activeWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("3-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("3-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageが存在する場合、そのページを取得すること。
    // Given: refresh = false, savedNextPage = 3, apiNextPage = null
    // When: loadActiveWeeklyPrizes
    // Then: emit activeWeeklyPrizes = [3-1, 3-2], hasNext = false
    @Test
    fun givenLastResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForLastPageAndHasNotNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = null,
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateActiveWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadActiveWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.activeWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("3-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("3-2", activeWeeklyPrizes.prizes[1].id)
            assertFalse(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: Refreshすると前回のnextPageを無視して１ページ目を取得すること
    // Given: refresh = true, savedNextPage = 3, apiNextPage = 2
    // When: loadActiveWeeklyPrizes
    // Then: emit activeWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext2() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateActiveWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadActiveWeeklyPrizes(refresh = true)

            // Then
            val activeWeeklyPrizes = repository.activeWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("1-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("1-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: Flow制御
    // Name: リフレッシュしない場合は、懸賞一覧の更新とNextPageの更新がある
    // Given: refresh = false
    // When: loadActiveWeeklyPrizes
    // Then: emit activeWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext3() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            val repository = createRepository()

            repository.activeWeeklyPrizes.test {
                // When
                repository.loadActiveWeeklyPrizes(refresh = false)

                // Then
                // Flowの順番を検証する(空状態の懸賞一覧→懸賞一覧&hasNext=false→hasNext=true）
                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertTrue(it.hasNext)
                }
            }
        }

    // Category: Flow制御
    // Name: リフレッシュする場合は、はじめにemptyでクリアし、懸賞一覧の更新とNextPageの更新がある
    // Given: refresh = true
    // When: loadActiveWeeklyPrizes
    // Then: emit empty and activeWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadActiveWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext4() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            val repository = createRepository()

            prizePageLocalDataSource.updateActiveWeeklyPrizesNextPage(2)
            prizeLocalDataSource.updateActiveWeeklyPrizes(
                listOf(
                    Prize::class.fixture(id = "2-1"),
                    Prize::class.fixture(id = "2-2"),
                ),
                false,
            )

            repository.activeWeeklyPrizes.test {
                // When
                repository.loadActiveWeeklyPrizes(refresh = true)

                // Then
                // Flowの順番を検証する(2件の懸賞→空状態の懸賞一覧→懸賞一覧→hasNext=false→hasNext=true）
                awaitItem().let {
                    assertEquals(2, it.prizes.size)
                    assertTrue(it.hasNext)
                }

                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertTrue(it.hasNext)
                }

                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertTrue(it.hasNext)
                }
            }
        }

    // AppliedPrizes

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageがnullの場合、１ページ目を取得すること。
    // Given: refresh = false, savedNextPage = null, apiNextPage = 2
    // When: loadAppliedWeeklyPrizes
    // Then: emit appliedWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }

            // When
            val repository = createRepository()
            repository.loadAppliedWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.appliedWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("1-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("1-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageが存在する場合、そのページを取得すること。
    // Given: refresh = false, savedNextPage = 3, apiNextPage = 4
    // When: loadAppliedWeeklyPrizes
    // Then: emit appliedWeeklyPrizes = [3-1, 3-2], hasNext = true
    @Test
    fun givenSecondResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForSecondPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = 4,
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateAppliedWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadAppliedWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.appliedWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("3-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("3-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: リフレッシュでなく、前回のnextPageが存在する場合、そのページを取得すること。
    // Given: refresh = false, savedNextPage = 3, apiNextPage = null
    // When: loadAppliedWeeklyPrizes
    // Then: emit appliedWeeklyPrizes = [3-1, 3-2], hasNext = false
    @Test
    fun givenLastResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForLastPageAndHasNotNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = null,
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateAppliedWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadAppliedWeeklyPrizes(refresh = false)

            // Then
            val activeWeeklyPrizes = repository.appliedWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("3-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("3-2", activeWeeklyPrizes.prizes[1].id)
            assertFalse(activeWeeklyPrizes.hasNext)
        }

    // Category: ページ数制御
    // Name: Refreshすると前回のnextPageを無視して１ページ目を取得すること
    // Given: refresh = true, savedNextPage = 3, apiNextPage = 2
    // When: loadAppliedWeeklyPrizes
    // Then: emit appliedWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext2() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            prizePageLocalDataSource.updateAppliedWeeklyPrizesNextPage(3)

            // When
            val repository = createRepository()
            repository.loadAppliedWeeklyPrizes(refresh = true)

            // Then
            val activeWeeklyPrizes = repository.appliedWeeklyPrizes.first()
            assertEquals(2, activeWeeklyPrizes.prizes.size)
            assertEquals("1-1", activeWeeklyPrizes.prizes[0].id)
            assertEquals("1-2", activeWeeklyPrizes.prizes[1].id)
            assertTrue(activeWeeklyPrizes.hasNext)
        }

    // Category: Flow制御
    // Name: リフレッシュしない場合は、懸賞一覧の更新とNextPageの更新がある
    // Given: refresh = false
    // When: loadAppliedWeeklyPrizes
    // Then: emit appliedWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext3() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            val repository = createRepository()

            repository.appliedWeeklyPrizes.test {
                // When
                repository.loadAppliedWeeklyPrizes(refresh = false)

                // Then
                // Flowの順番を検証する(空状態の懸賞一覧→懸賞一覧&hasNext=false→hasNext=true）
                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertTrue(it.hasNext)
                }
            }
        }

    // Category: Flow制御
    // Name: リフレッシュする場合は、はじめにemptyでクリアし、懸賞一覧の更新とNextPageの更新がある
    // Given: refresh = true
    // When: loadAppliedWeeklyPrizes
    // Then: emit empty and appliedWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadAppliedWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext4() =
        runTest(UnconfinedTestDispatcher()) {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(id = "$page-1"),
                            dummyPrizeResponse.copy(id = "$page-2"),
                        ),
                    )
                }
            }
            val repository = createRepository()

            prizePageLocalDataSource.updateAppliedWeeklyPrizesNextPage(2)
            prizeLocalDataSource.updateAppliedWeeklyPrizes(
                listOf(
                    Prize::class.fixture(id = "2-1"),
                    Prize::class.fixture(id = "2-2"),
                ),
                false,
            )

            repository.appliedWeeklyPrizes.test {
                // When
                repository.loadAppliedWeeklyPrizes(refresh = true)

                // Then
                // Flowの順番を検証する(2件の懸賞→空状態の懸賞一覧→懸賞一覧→hasNext=false→hasNext=true）
                awaitItem().let {
                    assertEquals(2, it.prizes.size)
                    assertTrue(it.hasNext)
                }

                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertTrue(it.hasNext)
                }

                awaitItem().let {
                    assertEquals(0, it.prizes.size)
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertFalse(it.hasNext)
                }

                awaitItem().let {
                    assertContentEquals(
                        listOf("1-1", "1-2"),
                        it.prizes.map { item -> item.id },
                    )
                    assertTrue(it.hasNext)
                }
            }
        }

    // Name: リフレッシュでなく、前回のnextPageがnullの場合、１ページ目を取得すること。
    // Given: refresh = false, savedNextPage = null, apiNextPage = 2
    // When: loadActiveDailyPrizes
    // Then: emit activeDailyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadActiveDailyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(
                                id = "$page-1",
                                type = CoinPrizeTypeResponse.Daily,
                            ),
                            dummyPrizeResponse.copy(
                                id = "$page-2",
                                type = CoinPrizeTypeResponse.Daily,
                            ),
                        ),
                    )
                }
            }

            // When
            val repository = createRepository()
            repository.loadActiveDailyPrizes(refresh = false)

            // Then
            val activeDailyPrizes = repository.activeDailyPrizes.first()
            assertEquals(2, activeDailyPrizes.prizes.size)
            assertEquals("1-1", activeDailyPrizes.prizes[0].id)
            assertEquals("1-2", activeDailyPrizes.prizes[1].id)
            assertTrue(activeDailyPrizes.hasNext)
        }

    // Name: リフレッシュでなく、前回のnextPageがnullの場合、１ページ目を取得すること。
    // Given: refresh = false, savedNextPage = null, apiNextPage = 2
    // When: loadUpcomingWeeklyPrizes
    // Then: emit upcomingWeeklyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadUpcomingWeeklyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                prizesMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(
                                id = "$page-1",
                                type = CoinPrizeTypeResponse.Weekly,
                            ),
                            dummyPrizeResponse.copy(
                                id = "$page-2",
                                type = CoinPrizeTypeResponse.Weekly,
                            ),
                        ),
                    )
                }
            }

            // When
            val repository = createRepository()
            repository.loadUpcomingWeeklyPrizes(refresh = false)

            // Then
            val upcomingWeeklyPrizes = repository.upcomingWeeklyPrizes.first()
            assertEquals(2, upcomingWeeklyPrizes.prizes.size)
            assertEquals("1-1", upcomingWeeklyPrizes.prizes[0].id)
            assertEquals("1-2", upcomingWeeklyPrizes.prizes[1].id)
            assertTrue(upcomingWeeklyPrizes.hasNext)
        }

    // Name: リフレッシュでなく、前回のnextPageがnullの場合、１ページ目を取得すること。
    // Given: refresh = false, savedNextPage = null, apiNextPage = 2
    // When: loadAppliedDailyPrizes
    // Then: emit appliedDailyPrizes = [1-1, 1-2], hasNext = true
    @Test
    fun givenFirstResponse_whenLoadAppliedDailyPrizes_thenFlowEmittedTwoPrizesForFirstPageAndHasNext() =
        runTest {
            // Given
            api = KyashCoinApiMock().apply {
                appliedMock = { _, page, _ ->
                    CoinPrizeListResponse(
                        nextPage = page?.plus(1),
                        coinPrizes = listOf(
                            dummyPrizeResponse.copy(
                                id = "$page-1",
                                type = CoinPrizeTypeResponse.Daily,
                            ),
                            dummyPrizeResponse.copy(
                                id = "$page-2",
                                type = CoinPrizeTypeResponse.Daily,
                            ),
                        ),
                    )
                }
            }

            // When
            val repository = createRepository()
            repository.loadAppliedDailyPrizes(refresh = false)

            // Then
            val appliedDailyPrizes = repository.appliedDailyPrizes.first()
            assertEquals(2, appliedDailyPrizes.prizes.size)
            assertEquals("1-1", appliedDailyPrizes.prizes[0].id)
            assertEquals("1-2", appliedDailyPrizes.prizes[1].id)
            assertTrue(appliedDailyPrizes.hasNext)
        }

    private fun createRepository(): PrizeRepository {
        return PrizeRepositoryImpl(
            prizeLocalDataSource,
            prizePageLocalDataSource,
            api,
        )
    }
}

class KyashCoinApiMock : KyashCoinApi {

    lateinit var appliedMock: (type: String, page: Int?, limit: Int) -> CoinPrizeListResponse
    lateinit var prizesMock: (type: String, page: Int?, limit: Int) -> CoinPrizeListResponse

    override suspend fun getAppliedPrizes(
        type: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse> {
        return Response(
            200,
            200,
            result = Response.Result(appliedMock(type, page, limit)),
        )
    }

    override suspend fun getPrizes(
        type: String,
        status: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse> {
        return Response(
            200,
            200,
            result = Response.Result(prizesMock(type, page, limit)),
        )
    }

    override suspend fun getDailyRouletteStatus(): Response<DailyRouletteStatusResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getCurrentKyashCoin(): Response<CurrentKyashCoinResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getUncheckedPrizes(): Response<UncheckedCoinPrizeResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getPrize(id: String): Response<CoinPrizeResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun applyCoinPrize(prizeId: String): Response<ApplyCoinPrizeResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getDailyRouletteOptions(): Response<DailyRouletteOptionsResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getDailyRouletteResult(): Response<DailyRouletteResultResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun sharedToSns(prizeId: String) {
        throw NotImplementedError("Unused")
    }

    override suspend fun receivePrize(applicationId: String): Response<ReceivePrizeResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun checkLotteryResult(prizeId: String) {
        throw NotImplementedError("Unused")
    }

    override suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<KyashCoinHistoryListResponse> {
        throw NotImplementedError("Unused")
    }

    override suspend fun getStampCardList(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<StampCardListResponse> {
        throw NotImplementedError("Unused")
    }
}

val dummyPrizeResponse = CoinPrizeResponse(
    id = "id",
    title = "title",
    imageUrl = "imageUrl",
    description = "description",
    entryCoinAmount = 100,
    status = CoinPrizeApplicationStatusResponse.NotApplied,
    appliedCount = 0,
    winnersCount = 0,
    maxWinnersCount = 0,
    type = CoinPrizeTypeResponse.Weekly,
    activationStatus = CoinPrizeActivationStatusResponse.Active,
    applicationId = null,
    lotteryResultChecked = false,
    appliedAt = null,
    receivedAt = null,
    startAt = DateTimeTz.nowLocal(),
    endAt = DateTimeTz.nowLocal(),
    receiveExpireAt = null,
    announceAt = DateTimeTz.nowLocal(),
    lotteryResultCheckExpireAt = null,
)
