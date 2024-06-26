package com.quinti.android_step_template.kmp.test

import co.kyash.mobile.data.api.exception.ApiFatalError
import co.kyash.mobile.data.api.exception.ApiFatalErrorException
import co.kyash.mobile.fixture.fixture
import co.kyash.mobile.model.reward.AppliedPrizeFilter
import co.kyash.mobile.model.reward.Prize
import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.reward.prize.AppliedPrizeListReactor.Action
import co.kyash.mobile.reward.prize.AppliedPrizeListReactor.Event
import co.kyash.mobile.reward.prize.AppliedPrizeListReactor.State
import co.kyash.mobile.ui.HasTestRules
import co.kyash.mobile.ui.MainDispatcherRule
import co.kyash.mobile.ui.Reactor
import co.kyash.mobile.ui.TestRules
import co.kyash.mobile.ui.assertType
import co.kyash.mobile.ui.test
import co.kyash.mobile.ui.usecase.GetUncheckRewardCountMock
import co.kyash.mobile.ui.usecase.QueryAppliedDailyPrizesMock
import co.kyash.mobile.ui.usecase.QueryAppliedWeeklyPrizesMock
import co.kyash.mobile.ui.usecase.RefreshAppliedDailyPrizesMock
import co.kyash.mobile.ui.usecase.RefreshAppliedWeeklyPrizesMock
import co.kyash.mobile.ui.usecase.WatchAppliedDailyCoinPrizesMock
import co.kyash.mobile.ui.usecase.WatchAppliedWeeklyPrizesMock
import co.kyash.mobile.usecase.reward.GetUncheckRewardCountUseCase
import co.kyash.mobile.usecase.reward.prizes.RefreshAppliedDailyPrizesUseCase
import co.kyash.mobile.usecase.reward.prizes.RefreshAppliedWeeklyPrizesUseCase
import co.kyash.mobile.usecase.reward.prizes.WatchAppliedDailyPrizesUseCase
import co.kyash.mobile.usecase.reward.prizes.WatchAppliedWeeklyPrizesUseCase
import korlibs.time.DateTimeTz
import korlibs.time.days
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest

class AppliedPrizeListReactorTest : HasTestRules {

    override val testRules: TestRules = TestRules(MainDispatcherRule())

    private lateinit var watchAppliedWeeklyPrizesUseCase: WatchAppliedWeeklyPrizesUseCase
    private lateinit var watchAppliedDailyPrizesUseCase: WatchAppliedDailyPrizesUseCase
    private lateinit var refreshAppliedWeeklyPrizesUseCase: RefreshAppliedWeeklyPrizesUseCase
    private lateinit var refreshAppliedDailyPrizesUseCase: RefreshAppliedDailyPrizesUseCase
    private lateinit var queryAppliedWeeklyPrizes: QueryAppliedWeeklyPrizesMock
    private lateinit var queryAppliedDailyCoinPrizes: QueryAppliedDailyPrizesMock
    private lateinit var getUncheckRewardCountUseCase: GetUncheckRewardCountUseCase

    @BeforeTest
    fun setUp() {
        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = {
                flow { }
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = {
                flow { }
            }
        }

        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {}
        }

        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {}
        }

        queryAppliedWeeklyPrizes = QueryAppliedWeeklyPrizesMock().apply {
            mock = {}
        }

        queryAppliedDailyCoinPrizes = QueryAppliedDailyPrizesMock().apply {
            mock = {}
        }
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = { 0 }
        }
    }

    // Given: なし
    // When: TapNavigationをタップした時
    // Then: Backイベントが発行される
    @Test
    fun givenNothing_whenTapNavigation_thenBackEventEmitted() = runTest {
        val reactor = createReactor()
        reactor.test {
            reactor.execute(Action.TapNavigation)
            skipStateItems(1)
            assertIs<Event.Back>(awaitEvent())
        }
    }

    // Given: 正常取得
    // When: 初期化時
    // Then: StateのWeeklyTabとDailyTabが更新される
    @Test
    fun givenSuccess_whenLoadInitially_thenStateUpdatedAndWeeklyTabAndDailyTabUpdated() = runTest {
        val weeklyFlow = MutableSharedFlow<PrizeList>()
        val dailyFlow = MutableSharedFlow<PrizeList>()

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weeklyFlow }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { dailyFlow }
        }

        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weeklyFlow.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "weekly",
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                dailyFlow.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "daily",
                                type = Prize.Type.Daily,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.weeklyTab.prizeList)
                assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
                assertEquals(0, value.weeklyTab.uncheckedCount)
                assertEquals(0, value.dailyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loaded>(value.weeklyTab.prizeList)
                assertIs<State.LoadableList.Loaded>(value.dailyTab.prizeList)
            }
        }
    }

    // Given: Weekly取得失敗（FatalError）
    // When: 初期化時
    // Then: Stateはエラーとなり、Event.HandleFatalErrorが発行される
    @Test
    fun givenFatalError_whenLoadInitially_thenStateErrorAndHandleFatalErrorEventEmitted() =
        runTest {
            refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
                mock = {
                    throw ApiFatalErrorException(ApiFatalError.Banned)
                }
            }
            val reactor = createReactor()
            reactor.test {
                assertIs<Reactor.LoadState.Loading<State>>(awaitState())
                reactor.execute(Action.LoadInitially)
                assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                    assertIs<State.LoadableList.Loading>(value.weeklyTab.prizeList)
                    assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
                    assertEquals(0, value.weeklyTab.uncheckedCount)
                    assertEquals(0, value.dailyTab.uncheckedCount)
                }
                assertIs<Reactor.LoadState.Error<State>>(awaitState())
                assertIs<Event.HandleFatalError>(awaitEvent())
            }
        }

    // Given: Weekly取得失敗（ShowError）
    // When: 初期化時
    // Then: Stateはエラーとなり、Event.ShowErrorが発行される
    @Test
    fun givenError_whenLoadInitially_thenStateErrorAndShowErrorEventEmitted() = runTest {
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                error("error")
            }
        }
        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
            }
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: GetUncheckedCount取得失敗（Error）
    // When: 初期化時
    // Then: Stateはエラーとなり、Event.ShowErrorが発行される
    @Test
    fun givenGetUncheckedCountError_whenLoadInitially_thenStateErrorAndShowErrorEventEmitted() =
        runTest {
            getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
                mock = {
                    error("error")
                }
            }
            val reactor = createReactor()
            reactor.test {
                assertIs<Reactor.LoadState.Loading<State>>(awaitState())
                reactor.execute(Action.LoadInitially)
                assertIs<Reactor.LoadState.Error<State>>(awaitState())
                assertIs<Event.ShowError>(awaitEvent())
            }
        }

    // Given: GetUncheckedCount取得失敗（FatalError）
    // When: 初期化時
    // Then: Stateはエラーとなり、Event.ShowErrorが発行される
    @Test
    fun givenGetUncheckedCountFatalError_whenLoadInitially_thenStateErrorAndShowErrorEventEmitted() =
        runTest {
            getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
                mock = {
                    throw ApiFatalErrorException(ApiFatalError.Banned)
                }
            }
            val reactor = createReactor()
            reactor.test {
                assertIs<Reactor.LoadState.Loading<State>>(awaitState())
                reactor.execute(Action.LoadInitially)
                assertIs<Reactor.LoadState.Error<State>>(awaitState())
                assertIs<Event.HandleFatalError>(awaitEvent())
            }
        }

    // Given: デイリーチャレンジ
    // When: 懸賞タップ
    // Then: 懸賞詳細画面へ遷移するイベントが発行される
    @Test
    fun givenDailyChallenge_whenTapPrize_thenNavigateToPrizeDetailEventEmitted() = runTest {
        val reactor = createReactor()
        reactor.test {
            reactor.execute(Action.LoadInitially)
            skipStateItems(2)
            reactor.execute(
                Action.TapAppliedPrize(
                    State.AppliedPrizeItem(
                        Prize::class.fixture(
                            type = Prize.Type.Daily,
                            status = Prize.ApplicationStatus.Applied(
                                DateTimeTz.nowLocal(),
                                "applicationId",
                            ),
                        ),
                    ),
                ),
            )
            assertIs<Event.NavigateToPrizeDetail>(awaitEvent())
        }
    }

    // Given: ウィークリーチャレンジ(懸賞結果をチェック済みの場合)
    // When: 懸賞タップ
    // Then: 懸賞詳細画面へ遷移するイベントが発行される
    @Test
    fun givenWeeklyChallengeAndLotteryResultChecked_whenTapPrize_thenNavigateToPrizeDetailEventEmitted() =
        runTest {
            val reactor = createReactor()
            reactor.test {
                reactor.execute(Action.LoadInitially)
                skipStateItems(2)
                reactor.execute(
                    Action.TapAppliedPrize(
                        State.AppliedPrizeItem(
                            Prize::class.fixture(
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                                lotteryResultChecked = true,
                            ),
                        ),
                    ),
                )
                assertIs<Event.NavigateToPrizeDetail>(awaitEvent())
            }
        }

    // Given: ウィークリーチャレンジ(懸賞結果をチェックしていない場合)
    // When: 懸賞タップ
    // Then: 当選結果画面へ遷移するイベントが発行される
    @Test
    fun givenWeeklyChallengeAndLotteryResultNotChecked_whenTapPrize_thenShowApplicationResultEventEmitted() =
        runTest {
            val reactor = createReactor()
            reactor.test {
                reactor.execute(Action.LoadInitially)
                skipStateItems(2)
                reactor.execute(
                    Action.TapAppliedPrize(
                        State.AppliedPrizeItem(
                            Prize::class.fixture(
                                type = Prize.Type.Weekly,
                                activationStatus = Prize.ActivationStatus.Announced,
                                status = Prize.ApplicationStatus.Won(
                                    appliedAt = DateTimeTz.nowLocal(),
                                    DateTimeTz.nowLocal(),
                                    DateTimeTz.nowLocal() + 1.days,
                                    "applicationId",
                                ),
                                lotteryResultChecked = false,
                            ),
                        ),
                    ),
                )
                assertIs<Event.ShowApplicationResult>(awaitEvent())
            }
        }

    // Given: 正常取得
    // When: リフレッシュ
    // Then: 新しい結果を取得し、StateのWeeklyTabとDailyTabが更新される
    @Test
    fun givenSuccess_whenRefresh_thenStateUpdatedAndWeeklyTabAndDailyTabUpdated() = runTest {
        val weekly = MutableSharedFlow<PrizeList>()
        val daily = MutableSharedFlow<PrizeList>()
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                if (count == 0) {
                    0
                } else {
                    1
                }
            }
        }
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.Refresh)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(1, value.weeklyTab.uncheckedCount)
                assertTrue(value.isRefreshing)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("refresh", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
        }
    }

    // Given: リフレッシュ時にFatalError
    // When: リフレッシュ
    // Then: Stateはエラーとなり、Event.HandleFatalErrorが発行される
    @Test
    fun givenFatalError_whenRefresh_thenStateErrorAndHandleFatalErrorEventEmitted() = runTest {
        val weekly = MutableSharedFlow<PrizeList>()
        val daily = MutableSharedFlow<PrizeList>()
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        throw ApiFatalErrorException(ApiFatalError.Banned)
                    },
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.Refresh)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertEquals(0, value.weeklyTab.uncheckedCount)
                assertTrue(value.isRefreshing)
            }
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.HandleFatalError>(awaitEvent())
        }
    }

    // Given: リフレッシュ時にError
    // When: リフレッシュ
    // Then: Stateはエラーとなり、Event.ShowErrorが発行される
    @Test
    fun givenError_whenRefresh_thenStateErrorAndShowErrorEventEmitted() = runTest {
        val weekly = MutableSharedFlow<PrizeList>()
        val daily = MutableSharedFlow<PrizeList>()
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        error("error")
                    },
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.Refresh)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertTrue(value.isRefreshing)
            }
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: リフレッシュ時にGetUncheckedCountがError
    // When: リフレッシュ
    // Then: Stateはエラーとなり、Event.ShowErrorが発行される
    @Test
    fun givenGetUncheckedCountError_whenRefresh_thenStateErrorAndShowErrorEventEmitted() = runTest {
        val weekly = MutableSharedFlow<PrizeList>()
        val daily = MutableSharedFlow<PrizeList>()
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        error("error")
                    },
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.Refresh)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertTrue(value.isRefreshing)
            }
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: 正常取得
    // When: 状態リフレッシュ
    // Then: UncheckedCountを再取得する
    @Test
    fun givenSuccess_whenRefreshState_thenUncheckedCountUpdated() = runTest {
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                if (count == 0) {
                    0
                } else {
                    1
                }
            }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            reactor.execute(Action.RefreshStatus)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(1, value.weeklyTab.uncheckedCount)
                assertFalse(value.isRefreshing)
            }
        }
    }

    // Given: エラー
    // When: 状態リフレッシュ
    // Then: ShowErrorイベントが発行される
    @Test
    fun givenError_whenRefreshState_thenShowErrorEventEmitted() = runTest {
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                error("error")
            }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            reactor.execute(Action.RefreshStatus)
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: Fatalエラー
    // When: 状態リフレッシュ
    // Then: ShowErrorイベントが発行される
    @Test
    fun givenFatalError_whenRefreshState_thenShowErrorEventEmitted() = runTest {
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                throw ApiFatalErrorException(ApiFatalError.Banned)
            }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            reactor.execute(Action.RefreshStatus)
            assertIs<Reactor.LoadState.Error<State>>(awaitState())
            assertIs<Event.HandleFatalError>(awaitEvent())
        }
    }

    // Given: 正常系
    // When: 追加ローディング（Weekly）
    // Then: 追加ローディング状態になり、StateのWeeklyTabが更新される
    @Test
    fun givenSuccess_whenLoadMoreWeekly_thenStateUpdatedAndWeeklyTabUpdated() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(

                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Daily,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        queryAppliedWeeklyPrizes = QueryAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    weekly.value?.let {
                        it.copy(
                            prizes = it.prizes + listOf(
                                Prize::class.fixture(
                                    id = "loadMore",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        appliedAt = DateTimeTz.nowLocal(),
                                        applicationId = "",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)

            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.LoadNext.Weekly)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals(2, list.size)
                    assertEquals("loadMore", list.last().prizeId)
                    assertEquals("initial", list.first().prizeId)
                }
                assertEquals(true, value.weeklyTab.hasMore)
                assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                    assertEquals(1, list.size)
                }
                assertFalse(value.isRefreshing)
            }
        }
    }

    // Given: 異常系（FatalError）
    // When: 追加ローディング（Weekly）
    // Then: Stateには追加されず、Event.HandleFatalErrorが発行される
    @Test
    fun givenFatalError_whenLoadMoreWeekly_thenStateNotUpdatedAndHandleFatalErrorEventEmitted() =
        runTest {
            val weekly = MutableStateFlow<PrizeList?>(null)
            val daily = MutableStateFlow<PrizeList?>(null)
            refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
                mock = {
                    daily.emit(

                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        ),
                    )
                }
            }
            refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
                mock = {
                    weekly.emit(
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        ),
                    )
                }
            }

            queryAppliedWeeklyPrizes = QueryAppliedWeeklyPrizesMock().apply {
                mock = {
                    throw ApiFatalErrorException(ApiFatalError.Banned)
                }
            }

            watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
                mock = { weekly.filterNotNull() }
            }

            watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
                mock = { daily.filterNotNull() }
            }

            val reactor = createReactor()
            reactor.test {
                assertIs<Reactor.LoadState.Loading<State>>(awaitState())
                reactor.execute(Action.LoadInitially)

                assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                    assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
                }
                assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                    assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                        assertEquals("initial", list.first().prizeId)
                    }
                    assertFalse(value.isRefreshing)
                }
                reactor.execute(Action.LoadNext.Weekly)
                assertIs<Event.HandleFatalError>(awaitEvent())
            }
        }

    // Given: 異常系（ShowError）
    // When: 追加ローディング（Weekly）
    // Then: Stateには追加されず、Event.ShowErrorが発行される
    @Test
    fun givenError_whenLoadMoreWeekly_thenStateNotUpdatedAndShowErrorEventEmitted() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(

                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Daily,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        queryAppliedWeeklyPrizes = QueryAppliedWeeklyPrizesMock().apply {
            mock = {
                error("error")
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.weeklyTab.prizeList)
                assertFalse(value.isRefreshing)
            }

            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.LoadNext.Weekly)
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: 正常系
    // When: 追加ローディング（Daily）
    // Then: 追加ローディング状態になり、StateのDailyTabが更新される
    @Test
    fun givenSuccess_whenLoadMoreDaily_thenStateUpdatedAndDailyTabUpdated() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(

                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Daily,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        queryAppliedDailyCoinPrizes = QueryAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    daily.value?.let {
                        it.copy(
                            prizes = it.prizes + listOf(
                                Prize::class.fixture(
                                    id = "loadMore",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        appliedAt = DateTimeTz.nowLocal(),
                                        applicationId = "",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.LoadNext.Daily)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                    assertEquals(2, list.size)
                    assertEquals("initial", list.first().prizeId)
                    assertEquals("loadMore", list.last().prizeId)
                }
                assertEquals(true, value.dailyTab.hasMore)
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals(1, list.size)
                }
                assertFalse(value.isRefreshing)
            }
        }
    }

    // Given: 異常系（FatalError）
// When: 追加ローディング（Daily）
// Then: Stateには追加されず、Event.HandleFatalErrorが発行される
    @Test
    fun givenFatalError_whenLoadMoreDaily_thenStateNotUpdatedAndHandleFatalErrorEventEmitted() =
        runTest {
            val weekly = MutableStateFlow<PrizeList?>(null)
            val daily = MutableStateFlow<PrizeList?>(null)
            refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
                mock = {
                    daily.emit(
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        ),
                    )
                }
            }
            refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
                mock = {
                    weekly.emit(
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        ),
                    )
                }
            }

            queryAppliedDailyCoinPrizes = QueryAppliedDailyPrizesMock().apply {
                mock = {
                    throw ApiFatalErrorException(ApiFatalError.Banned)
                }
            }

            watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
                mock = { weekly.filterNotNull() }
            }

            watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
                mock = { daily.filterNotNull() }
            }

            val reactor = createReactor()
            reactor.test {
                assertIs<Reactor.LoadState.Loading<State>>(awaitState())
                reactor.execute(Action.LoadInitially)
                assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                    assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
                }
                assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                    assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                        assertEquals("initial", list.first().prizeId)
                    }
                    assertFalse(value.isRefreshing)
                }
                reactor.execute(Action.LoadNext.Daily)
                assertIs<Event.HandleFatalError>(awaitEvent())
            }
        }

    // Given: 異常系（ShowError）
// When: 追加ローディング（Daily）
// Then: Stateには追加されず、Event.ShowErrorが発行される
    @Test
    fun givenError_whenLoadMoreDaily_thenStateNotUpdatedAndShowErrorEventEmitted() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(

                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Daily,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    PrizeList(
                        true,
                        prizes = listOf(
                            Prize::class.fixture(
                                id = "initial",
                                type = Prize.Type.Weekly,
                                status = Prize.ApplicationStatus.Applied(
                                    DateTimeTz.nowLocal(),
                                    "applicationId",
                                ),
                            ),
                        ),
                    ),
                )
            }
        }

        queryAppliedDailyCoinPrizes = QueryAppliedDailyPrizesMock().apply {
            mock = {
                error("error")
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)

            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertIs<State.LoadableList.Loading>(value.dailyTab.prizeList)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.LoadNext.Daily)
            assertIs<Event.ShowError>(awaitEvent())
        }
    }

    // Given: 空の応募履歴一覧を取得する
    // When: 初期化
    // Then: 正常に空の一覧がStateに設定されていること
    @Test
    fun givenLoadBeforeInitialize_whenInitialize_thenStateUpdated() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    PrizeList(
                        hasNext = false,
                        prizes = emptyList(),
                    ),
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    PrizeList(
                        hasNext = false,
                        prizes = emptyList(),
                    ),
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                1
            }
        }

        val reactor = createReactor()
        reactor.test {
            reactor.execute(Action.LoadInitially)
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(1, value.weeklyTab.uncheckedCount)
                assertIs<State.LoadableList.Loading>(value.weeklyTab.prizeList)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(1, value.weeklyTab.uncheckedCount)
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertTrue(list.isEmpty())
                }
            }
        }
    }

    // Given: 正常系
    // When: フィルター適応
    // Then: フィルター適応通りの値がStateに設定されていること
    @Test
    fun givenSuccess_whenSetFilter_thenStateUpdatedAndWeeklyTabAndDailyTabUpdated() = runTest {
        val weekly = MutableStateFlow<PrizeList?>(null)
        val daily = MutableStateFlow<PrizeList?>(null)
        val filter = AppliedPrizeFilter.Won
        getUncheckRewardCountUseCase = GetUncheckRewardCountMock().apply {
            mock = {
                if (count == 0) {
                    0
                } else {
                    1
                }
            }
        }
        refreshAppliedDailyPrizesUseCase = RefreshAppliedDailyPrizesMock().apply {
            mock = {
                daily.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        assertEquals(filter, it)
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Daily,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }
        refreshAppliedWeeklyPrizesUseCase = RefreshAppliedWeeklyPrizesMock().apply {
            mock = {
                weekly.emit(
                    if (count == 0) {
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "initial",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    } else {
                        assertEquals(filter, it)
                        PrizeList(
                            true,
                            prizes = listOf(
                                Prize::class.fixture(
                                    id = "refresh",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        DateTimeTz.nowLocal(),
                                        "applicationId",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        queryAppliedWeeklyPrizes = QueryAppliedWeeklyPrizesMock().apply {
            mock = {
                assertEquals(filter, it)
                weekly.emit(
                    weekly.value?.let {
                        it.copy(
                            prizes = it.prizes + listOf(
                                Prize::class.fixture(
                                    id = "loadMore",
                                    type = Prize.Type.Weekly,
                                    status = Prize.ApplicationStatus.Applied(
                                        appliedAt = DateTimeTz.nowLocal(),
                                        applicationId = "",
                                    ),
                                ),
                            ),
                        )
                    },
                )
            }
        }

        watchAppliedWeeklyPrizesUseCase = WatchAppliedWeeklyPrizesMock().apply {
            mock = { weekly.filterNotNull() }
        }

        watchAppliedDailyPrizesUseCase = WatchAppliedDailyCoinPrizesMock().apply {
            mock = { daily.filterNotNull() }
        }

        val reactor = createReactor()
        reactor.test {
            assertIs<Reactor.LoadState.Loading<State>>(awaitState())
            reactor.execute(Action.LoadInitially)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(0, value.weeklyTab.uncheckedCount)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("initial", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.ChangeFilter(filter, Prize.Type.Weekly))
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(filter, value.weeklyTab.filter)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertEquals(1, value.weeklyTab.uncheckedCount)
                assertTrue(value.isRefreshing)
            }
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals("refresh", list.first().prizeId)
                }
                assertFalse(value.isRefreshing)
            }
            reactor.execute(Action.LoadNext.Weekly)
            assertType<Reactor.LoadState.Loaded<State>>(awaitState()) {
                assertType<State.LoadableList.Loaded>(value.weeklyTab.prizeList) {
                    assertEquals(2, list.size)
                    assertEquals("loadMore", list.last().prizeId)
                    assertEquals("refresh", list.first().prizeId)
                    assertEquals(filter, value.weeklyTab.filter)
                }
                assertEquals(true, value.weeklyTab.hasMore)
                assertType<State.LoadableList.Loaded>(value.dailyTab.prizeList) {
                    assertEquals(1, list.size)
                }
                assertFalse(value.isRefreshing)
            }
        }
    }

    private fun createReactor() = AppliedPrizeListReactor(
        mainDispatcher = Dispatchers.Main,
        watchAppliedWeeklyPrizes = watchAppliedWeeklyPrizesUseCase,
        watchAppliedDailyPrizes = watchAppliedDailyPrizesUseCase,
        refreshAppliedWeeklyPrizes = refreshAppliedWeeklyPrizesUseCase,
        refreshAppliedDailyPrizes = refreshAppliedDailyPrizesUseCase,
        queryAppliedDailyPrizes = queryAppliedDailyCoinPrizes,
        queryAppliedWeeklyPrizes = queryAppliedWeeklyPrizes,
        getUncheckRewardCountUseCase = getUncheckRewardCountUseCase,
    )
}

class AppliedPrizeItemTest {

    // Given 応募済み発表後かつ懸賞結果をチェックしていない
    // When: unchecked
    // Then: 懸賞結果をチェックしていない状態となる
    @Test
    fun givenAppliedAndAnnouncedAndLotteryResultUnChecked_whenUnchecked_thenTrue() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Applied(
                    DateTimeTz.nowLocal(),
                    "applicationId",
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = false,
            ),
        )
        assertTrue(item.unchecked)
    }

    // Given 応募済み発表後かつ懸賞結果をチェックしている
    // When: unchecked
    // Then: 懸賞結果をチェックしている状態となる
    @Test
    fun givenAppliedAndAnnouncedAndLotteryResultChecked_whenUnchecked_thenFalse() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Applied(
                    DateTimeTz.nowLocal(),
                    "applicationId",
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = true,
            ),
        )
        assertFalse(item.unchecked)
    }

    // Given 応募済み
    // When: displayType
    // Then: 応募済み状態であること
    @Test
    fun givenApplied_whenDisplayType_thenApplied() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Applied(
                    DateTimeTz.nowLocal(),
                    "applicationId",
                ),
            ),
        )
        assertIs<AppliedPrizeStatus.Applied>(item.displayType)
    }

    // Given 懸賞結果確認期限切れ
    // When: displayType
    // Then: 懸賞結果確認期限切れ状態であること
    @Test
    fun givenLotteryResultCheckExpired_whenDisplayType_thenLotteryResultCheckExpired() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.LotteryResultCheckExpired(
                    appliedAt = DateTimeTz.nowLocal(),
                ),
            ),
        )
        assertIs<AppliedPrizeStatus.CheckExpired>(item.displayType)
    }

    // Given statusが受取期限切れ
    // When: displayType
    // Then: 受取期限切れ状態であること
    @Test
    fun givenStatusIsExpired_whenDisplayType_thenExpired() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.ReceiveExpired(
                    appliedAt = DateTimeTz.nowLocal(),
                ),
            ),
        )
        assertIs<AppliedPrizeStatus.ReceiveExpired>(item.displayType)
    }

    // Given statusが受取済み
    // When: displayType
    // Then: 受取済み状態であること
    @Test
    fun givenStatusIsReceived_whenDisplayType_thenReceived() {
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Received(
                    appliedAt = DateTimeTz.nowLocal(),
                    DateTimeTz.nowLocal(),
                    "applicationId",
                ),
            ),
        )
        assertIs<AppliedPrizeStatus.Received>(item.displayType)
    }

    // Given statusが落選かつ当選結果未確認
    // When: displayType
    // Then: 結果未確認（落選）状態であること
    @Test
    fun givenStatusIsNotWonAndLotteryResultNotChecked_whenDisplayType_thenNotChecked() {
        val expired = DateTimeTz.nowLocal()
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Lost(
                    appliedAt = DateTimeTz.nowLocal(),
                    lotteryResultCheckExpireAt = expired,
                    applicationId = "applicationId",
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = false,
            ),
        )
        assertIs<AppliedPrizeStatus.ResultNotChecked>(item.displayType)
    }

    // Given statusが落選かつ当選結果確認済み
    // When: displayType
    // Then: 落選状態であること
    @Test
    fun givenStatusIsNotWonAndLotteryResultChecked_whenDisplayType_thenLost() {
        val expired = DateTimeTz.nowLocal()
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Lost(
                    appliedAt = DateTimeTz.nowLocal(),
                    lotteryResultCheckExpireAt = expired,
                    applicationId = "applicationId",
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = true,
            ),
        )
        assertIs<AppliedPrizeStatus.Lost>(item.displayType)
    }

    // Given statusが当選かつ当選結果未確認
    // When: displayType
    // Then: 結果未確認（当選）状態であること
    @Test
    fun givenStatusIsWonAndLotteryResultNotChecked_whenDisplayType_thenNotChecked() {
        val expired = DateTimeTz.nowLocal()
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Won(
                    appliedAt = DateTimeTz.nowLocal(),
                    receiveExpireAt = expired,
                    applicationId = "applicationId",
                    lotteryResultCheckExpireAt = DateTimeTz.nowLocal() + 1.days,
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = false,
            ),
        )
        assertIs<AppliedPrizeStatus.ResultNotChecked>(item.displayType)
    }

    // Given statusが落選かつ当選結果確認済み
    // When: displayType
    // Then: 当選状態であること
    @Test
    fun givenStatusIsWonAndLotteryResultChecked_whenDisplayType_thenWon() {
        val expired = DateTimeTz.nowLocal()
        val item = State.AppliedPrizeItem(
            Prize::class.fixture(
                type = Prize.Type.Weekly,
                status = Prize.ApplicationStatus.Won(
                    appliedAt = DateTimeTz.nowLocal(),
                    receiveExpireAt = expired,
                    applicationId = "applicationId",
                    lotteryResultCheckExpireAt = DateTimeTz.nowLocal() + 1.days,
                ),
                activationStatus = Prize.ActivationStatus.Announced,
                lotteryResultChecked = true,
            ),
        )
        assertIs<AppliedPrizeStatus.Won>(item.displayType)
    }
}