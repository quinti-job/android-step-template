package com.quinti.android_step_template.kmp.domain.reactor

import com.quinti.android_step_template.kmp.data.entity.RewardChallengeStatus
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor.Action
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor.Event
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor.Mutation
import com.quinti.android_step_template.kmp.domain.reactor.RewardTopReactor.State
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.entity.StampCardUiState
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.kmp.data.api.exception.asNonFatalError
import com.quinti.android_step_template.kmp.data.entity.DailyRouletteStatus
import com.quinti.android_step_template.kmp.data.entity.KyashCoin
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.data.entity.PrizeApplicableStatus
import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.data.entity.RemainingTimeType
import com.quinti.android_step_template.kmp.data.entity.UncheckedPrizeSummary
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.transformIfLoaded
import com.quinti.android_step_template.kmp.domain.usecase.ExchangeStampCardRewardUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetCurrentStampCardUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetDailyRouletteStatusUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetKyashCoinAmountUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetPointBalanceUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetUncheckRewardCountUseCase
import com.quinti.android_step_template.kmp.domain.usecase.NeedShowRewardSpotlightUseCase
import com.quinti.android_step_template.kmp.domain.usecase.NeedShowRewardStampOnboardingUseCase
import com.quinti.android_step_template.kmp.domain.usecase.NeedShowRewardTopOnboardingUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshActiveDailyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshActiveWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshUpcomingWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshWelcomePrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RequestUpdateRewardTabIndicatorUseCase
import com.quinti.android_step_template.kmp.domain.usecase.SendShowRewardTopOnboardingEventUseCase
import com.quinti.android_step_template.kmp.domain.usecase.SetShowRewardStampOnboardingUseCase
import com.quinti.android_step_template.kmp.domain.usecase.SetShowRewardTopOnboardingUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchActiveDailyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchActiveWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchUpcomingWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchWelcomePrizesUseCase
import com.quinti.android_step_template.ui.component.PointBalance
import korlibs.time.DateTimeTz
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge

@Suppress("LongParameterList")
class RewardTopReactor(
    mainDispatcher: CoroutineDispatcher,
    private val getKyashCoinAmount: GetKyashCoinAmountUseCase,
    private val getDailyRouletteStatus: GetDailyRouletteStatusUseCase,
    private val getUncheckRewardCount: GetUncheckRewardCountUseCase,
    private val refreshActiveWeeklyPrizes: RefreshActiveWeeklyPrizesUseCase,
    private val refreshActiveDailyPrizes: RefreshActiveDailyPrizesUseCase,
    private val refreshUpcomingWeeklyPrizes: RefreshUpcomingWeeklyPrizesUseCase,
    private val refreshWelcomePrizes: RefreshWelcomePrizesUseCase,
    private val watchActiveWeeklyPrizes: WatchActiveWeeklyPrizesUseCase,
    private val watchActiveDailyPrizes: WatchActiveDailyPrizesUseCase,
    private val watchUpcomingWeeklyPrizes: WatchUpcomingWeeklyPrizesUseCase,
    private val watchWelcomePrizes: WatchWelcomePrizesUseCase,
    private val needShowRewardTopOnboarding: NeedShowRewardTopOnboardingUseCase,
    private val setShowRewardTopOnboarding: SetShowRewardTopOnboardingUseCase,
    private val sendShowRewardTopOnboardingFirstOpenEvent: SendShowRewardTopOnboardingEventUseCase,
    private val requestUpdateRewardTabIndicator: RequestUpdateRewardTabIndicatorUseCase,
    private val getCurrentStampCard: GetCurrentStampCardUseCase,
    private val exchangeStampCardReward: ExchangeStampCardRewardUseCase,
    private val getPointBalance: GetPointBalanceUseCase,
    private val needShowRewardSpotlight: NeedShowRewardSpotlightUseCase,
    private val needShowRewardStampOnboarding: NeedShowRewardStampOnboardingUseCase,
    private val setNeedShowRewardStampOnboarding: SetShowRewardStampOnboardingUseCase,
    private val isAutoNavigateToRouletteEnabled: Boolean,
    private val countDownEnabled: Boolean = false,
    private val isWelcomeChallengeEnabled: Boolean = false,
) : AbstractReactor<Reactor.LoadState<State>, Action, Mutation, Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {

    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> loadInitially()
            Action.Refresh -> refreshAll()
            is Action.TapPrize -> {
                readySpotlightIfNeed(SpotlightUiState.Show.WelcomeChallenge)
                notify(Event.NavigateToPrizeDetail(action.prize.prize))
            }

            Action.TapRoulette -> {
                readySpotlightIfNeed(SpotlightUiState.Show.Roulette)
                notify(Event.NavigateToDailyRoulette)
            }

            Action.TapAppliedPrizeList -> notify(Event.NavigateToAppliedPrizeList)
            Action.OnResume -> {
                refreshStatus()
                emit(Mutation.SetTickerEnabled(true))
                showSpotlightIfNeed()
            }

            Action.OnStop -> emit(Mutation.SetTickerEnabled(false))
            Action.RefreshPrizes -> refreshPrizes()
            Action.TapAboutReward -> notify(Event.NavigateToAboutReward)
            Action.TapKyashCoinDetail -> notify(Event.NavigateToKyashCoinDetail)
            Action.TapChallengeStamp -> tapChallengeStamp()
            Action.TapPointDetail -> notify(Event.NavigateToPointHistory)
            Action.TapSpotlightScrim -> {
                showNextSpotlight()
            }

            Action.TapOfferWall -> notify(Event.NavigateToOfferWall)
        }
    }

    @Suppress("LongMethod")
    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            Mutation.SetLoading -> Reactor.LoadState.Loading()
            Mutation.SetError -> Reactor.LoadState.Error()
            is Mutation.SetLoaded -> Reactor.LoadState.Loaded(
                State(
                    dailyRouletteStatus = mutation.dailyRouletteStatus,
                    kyashCoinAmount = mutation.currentKyashCoin.availableAmount,
                    weeklyPrizes = emptyList(),
                    dailyPrizes = emptyList(),
                    weeklyUpcomingPrizes = emptyList(),
                    welcomePrizes = emptyList(),
                    uncheckRewardCount = mutation.uncheckRewardCount,
                    pointBalance = mutation.pointBalance,
                    isRefreshing = false,
                    spotlightState = mutation.spotlightState,
                ),
            )

            is Mutation.RefreshStatus -> state.transformIfLoaded {
                it.copy(
                    dailyRouletteStatus = mutation.dailyRouletteStatus,
                    kyashCoinAmount = mutation.currentKyashCoin.availableAmount,
                    uncheckRewardCount = mutation.uncheckRewardCount,
                    pointBalance = mutation.pointBalance,
                    weeklyPrizes = it.weeklyPrizes.map { prizeUiState ->
                        prizeUiState.copy(
                            kyashCoinAmount = mutation.currentKyashCoin.availableAmount,
                        )
                    },
                    dailyPrizes = it.dailyPrizes.map { prizeUiState ->
                        prizeUiState.copy(
                            kyashCoinAmount = mutation.currentKyashCoin.availableAmount,
                        )
                    },
                    weeklyUpcomingPrizes = it.weeklyUpcomingPrizes.map { prizeUiState ->
                        prizeUiState.copy(
                            kyashCoinAmount = mutation.currentKyashCoin.availableAmount,
                        )
                    },
                )
            }

            is Mutation.SetRefreshing -> state.transformIfLoaded {
                it.copy(
                    isRefreshing = mutation.isRefreshing,
                )
            }

            is Mutation.NotifyRemainingTime -> state.transformIfLoaded {
                it.copy(
                    weeklyRemainingTimeType = mutation.weeklyRemainingTimeType,
                    dailyRemainingTimeType = mutation.dailyRemainingTimeType,
                    welcomeRemainingTimeType = mutation.welcomeRemainingTimeType,
                )
            }

            is Mutation.SetTickerEnabled -> state.transformIfLoaded {
                it.copy(
                    isTickerEnabled = mutation.isTickerEnabled,
                )
            }

            is Mutation.LoadPrizes -> state.transformIfLoaded {
                it.copy(
                    weeklyPrizes = mutation.weeklyPrizes.toUiStates(it.kyashCoinAmount),
                    dailyPrizes = mutation.dailyPrizes.toUiStates(it.kyashCoinAmount),
                    weeklyUpcomingPrizes = mutation.weeklyUpcomingPrizes
                        .toUiStates(it.kyashCoinAmount),
                    welcomePrizes = mutation.welcomePrizes.toUiStates(it.kyashCoinAmount),
                )
            }

            is Mutation.SetSpotlightState -> state.transformIfLoaded {
                it.copy(spotlightState = mutation.spotlightState)
            }
        }
    }

    private suspend fun FlowCollector<Mutation>.refreshAll() {
        emit(Mutation.SetRefreshing(true))
        runCatching {
            requestUpdateRewardTabIndicator()
            coroutineScope {
                val kyashCoinDeferred = async { getKyashCoinAmount() }
                val dailyRouletteStatusDeferred = async { getDailyRouletteStatus() }
                val pointBalanceDeferred = async { getPointBalance().getOrThrow() }

                val uncheckRewardCountDeferred = async {
                    getUncheckRewardCount()
                }

                emit(
                    Mutation.SetLoaded(
                        dailyRouletteStatus = dailyRouletteStatusDeferred.await(),
                        currentKyashCoin = kyashCoinDeferred.await(),
                        uncheckRewardCount = uncheckRewardCountDeferred.await(),
                        pointBalance = pointBalanceDeferred.await(),
                        spotlightState = SpotlightUiState.Hidden,
                    ),
                )
                awaitAll(
                    async { refreshActiveWeeklyPrizes() },
                    async { refreshActiveDailyPrizes() },
                    async { refreshUpcomingWeeklyPrizes() },
                    async { refreshWelcomePrizes() },
                )
            }
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
        }
        emit(Mutation.SetRefreshing(false))
    }

    private suspend fun FlowCollector<Mutation>.refreshPrizes() {
        runCatching {
            coroutineScope {
                awaitAll(
                    async { refreshActiveWeeklyPrizes() },
                    async { refreshActiveDailyPrizes() },
                    async { refreshUpcomingWeeklyPrizes() },
                    async { refreshWelcomePrizes() },
                )
            }
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
        }
    }

    private suspend fun FlowCollector<Mutation>.refreshStatus() {
        if (state.value !is Reactor.LoadState.Loaded<State>) return
        requestUpdateRewardTabIndicator()
        runCatching {
            coroutineScope {
                val kyashCoinDeferred = async { getKyashCoinAmount() }
                val dailyRouletteStatusDeferred = async { getDailyRouletteStatus() }
                val uncheckRewardCountDeferred = async {
                    getUncheckRewardCount()
                }
                val pointBalanceDeferred = async { getPointBalance().getOrThrow() }
                showStampCardDetailIfNeed()
                Mutation.RefreshStatus(
                    dailyRouletteStatus = dailyRouletteStatusDeferred.await(),
                    currentKyashCoin = kyashCoinDeferred.await(),
                    uncheckRewardCount = uncheckRewardCountDeferred.await(),
                    pointBalance = pointBalanceDeferred.await(),
                )
            }
        }.onSuccess {
            emit(it)
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
        }
    }

    private suspend fun FlowCollector<Mutation>.loadInitially() {
        val currentState = state.value
        if (currentState is Reactor.LoadState.Loaded) return

        emit(Mutation.SetLoading)

        runCatching {
            requestUpdateRewardTabIndicator()
            coroutineScope {
                val kyashCoinDeferred = async { getKyashCoinAmount() }
                val dailyRouletteStatusDeferred = async { getDailyRouletteStatus() }
                val needShowRewardTopOnboardingDeferred = async { needShowRewardTopOnboarding() }
                val uncheckRewardCountDeferred = async { getUncheckRewardCount() }
                val pointBalanceDeferred = async { getPointBalance().getOrThrow() }
                val dailyRouletteStatus = dailyRouletteStatusDeferred.await()
                val needShowRewardStampOnboardingDeferred = async {
                    needShowRewardStampOnboarding()
                }

                // スポットライトは表示しない
                var spotlightState: SpotlightUiState = SpotlightUiState.hidden
                // オンボーディングを必要あれば表示する
                if (needShowRewardTopOnboardingDeferred.await()) {
                    notify(Event.ShowRewardOnboarding)
                    val getNeedShowRewardSpotlightDeferred = async {
                        needShowRewardSpotlight()
                    }
                    val needShowSpotlight =
                        isWelcomeChallengeEnabled && getNeedShowRewardSpotlightDeferred.await()
                    if (needShowSpotlight) {
                        // Spotlightが有効なら表示可能状態とする
                        spotlightState = SpotlightUiState.ready
                        // 次に起動した際にスタンプカードオンボーディングを表示する
                        // 新オンボーディングが有効な新規ユーザーが対象
                        setNeedShowRewardStampOnboarding(true)
                    }
                    setShowRewardTopOnboarding()
                    sendShowRewardTopOnboardingFirstOpenEvent()
                } else if (needShowRewardStampOnboardingDeferred.await()) {
                    // スタンプカードのオンボーディングが必要な場合は表示する
                    notify(Event.ShowStampCardOnboarding)
                    // スタンプカードオンボーディングを表示しない
                    setNeedShowRewardStampOnboarding(false)
                } else if (dailyRouletteStatus.available && isAutoNavigateToRouletteEnabled) {
                    // デイリールーレットをまだ回していないならルーレット画面に遷移する
                    notify(Event.NavigateToDailyRoulette)
                } else {
                    // 未交換のスタンプがある場合はスタンプ画面を表示する
                    showStampCardDetailIfNeed()
                }

                // 全懸賞一覧を取得する
                execute(Action.RefreshPrizes)

                Mutation.SetLoaded(
                    dailyRouletteStatus = dailyRouletteStatus,
                    currentKyashCoin = kyashCoinDeferred.await(),
                    uncheckRewardCount = uncheckRewardCountDeferred.await(),
                    pointBalance = pointBalanceDeferred.await(),
                    spotlightState = spotlightState,
                )
            }
        }.onSuccess {
            emit(it)
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
            emit(Mutation.SetError)
        }
    }

    private suspend fun showStampCardDetailIfNeed() {
        val stampCard = getCurrentStampCard()
        if (stampCard != null && stampCard.hasNotExchangedStamp) {
            notify(Event.ShowStampCardDetail(StampCardUiState(stampCard)))
            exchangeStampCardReward(stampCard.id)
        }
    }

    private suspend fun tapChallengeStamp() {
        val stampCard = getCurrentStampCard()
        if (stampCard != null) {
            notify(Event.ShowStampCardDetail(StampCardUiState(stampCard)))
        }
    }

    /**
     * スポットライトを表示準備状態にする
     * 次に画面遷移から戻った場合(OnResume)にスポットライトを表示する。
     * @param target 今表示状態のスポットライトの指定、指定したスポットライト表示以外の場合は何もしない。
     */
    private suspend fun FlowCollector<Mutation>.readySpotlightIfNeed(
        target: SpotlightUiState.Show,
    ) {
        val currentState = state.value as? Reactor.LoadState.Loaded<State> ?: return
        val currentSpotlightState = currentState.value.spotlightState
        if (currentSpotlightState is SpotlightUiState.Show &&
            currentSpotlightState == target
        ) {
            emit(Mutation.SetSpotlightState(currentSpotlightState.ready()))
        }
    }

    /**
     * 表示準備状態のスポットライトを表示状態に変更し、表示イベントを発出する。
     * OnResume時に呼び出される。
     */
    @Suppress("ReturnCount")
    private suspend fun FlowCollector<Mutation>.showSpotlightIfNeed() {
        val currentState = state.value as? Reactor.LoadState.Loaded<State> ?: return
        val currentSpotlightState = currentState.value.spotlightState
        // Ready状態のみ表示する
        if (currentSpotlightState !is SpotlightUiState.Ready) {
            return
        }
        // 表示状態に変更する
        val next = currentSpotlightState.show()
        if (next == currentSpotlightState) {
            return
        }
        emit(Mutation.SetSpotlightState(next))
        notify(
            when (next) {
                SpotlightUiState.Show.Roulette -> Event.ShowSpotlight.Roulette
                SpotlightUiState.Show.WelcomeChallenge -> Event.ShowSpotlight.WelcomeChallenge
                else -> return
            },
        )
    }

    /**
     * 表示状態のスポットライトを次のスポットライトに変更し、表示イベントを発出する。
     */
    @Suppress("ReturnCount")
    private suspend fun FlowCollector<Mutation>.showNextSpotlight() {
        val currentState = state.value as? Reactor.LoadState.Loaded<State> ?: return
        val currentSpotlightState = currentState.value.spotlightState
        // 表示状態のみ表示する
        if (currentSpotlightState !is SpotlightUiState.Show) {
            return
        }
        val next = currentSpotlightState.next()
        if (next != currentSpotlightState) {
            emit(Mutation.SetSpotlightState(next))
            notify(
                when (next) {
                    SpotlightUiState.Show.Roulette -> Event.ShowSpotlight.Roulette
                    SpotlightUiState.Show.WelcomeChallenge -> Event.ShowSpotlight.WelcomeChallenge
                    else -> return
                },
            )
        }
    }

    override fun transformMutation(mutationFlow: Flow<Mutation>): Flow<Mutation> {
        return super.transformMutation(
            merge(
                mutationFlow,
                notifyRemainingTimeFlow,
                prizesFlow,
            ),
        )
    }

    private val prizesFlow: Flow<Mutation>
        // Reactorの初期化時に、タイミングによって懸賞一覧が先にemitされてしまい、
        // StateがLoading状態でStateに反映されない問題がある。
        // なので、Reactor.stateがLoadedになったときのみemitするようにする。
        get() = combine(
            watchActiveWeeklyPrizes(),
            watchActiveDailyPrizes(),
            watchUpcomingWeeklyPrizes(),
            watchWelcomePrizes(),
            state,
        ) { weekly, daily, upcoming, welcome, currentState ->
            if (currentState is Reactor.LoadState.Loaded) {
                Mutation.LoadPrizes(
                    weeklyPrizes = weekly,
                    dailyPrizes = daily,
                    weeklyUpcomingPrizes = upcoming,
                    // TODO(m-kato): WelcomeChallengeがリリースされたら削除する
                    welcomePrizes = if (isWelcomeChallengeEnabled) {
                        welcome
                    } else {
                        PrizeList(false, emptyList())
                    },
                )
            } else {
                null
            }
        }.filterNotNull()

    private val notifyRemainingTimeFlow: Flow<Mutation>
        get() = flow {

            // カウントダウンが無効なら何もしない
            if (!countDownEnabled) return@flow

            while (true) {
                if (state.value is Reactor.LoadState.Loaded) {
                    val stateValue = state.value as Reactor.LoadState.Loaded<State>

                    if (!stateValue.value.isTickerEnabled) {
                        // Tickerが無効なら何もしない
                        delay(RewardPrizeDetailReactor.TICKER_INTERVAL)
                        continue
                    }

                    // サーバーからはJSTとして時刻を取得するので、現在時刻もJSTに変換する
                    val value = stateValue.value
                    val now = DateTimeTz.nowLocal()

                    // 懸賞結果の発表時期を計算する
                    val weeklyRemainingTimeType = value.weeklyPrizes.firstOrNull()?.let {
                        RemainingTimeType.differenceAsRemainingTime(
                            targetTime = it.endAt,
                            now = now,
                        )
                    } ?: RemainingTimeType.Gone

                    val dailyRemainingTimeType = value.dailyPrizes.firstOrNull()?.let {
                        RemainingTimeType.differenceAsRemainingTime(
                            targetTime = it.endAt,
                            now = now,
                        )
                    } ?: RemainingTimeType.Gone

                    val welcomeRemainingTimeType = value.welcomePrizes.firstOrNull()?.let {
                        RemainingTimeType.differenceAsRemainingTime(
                            targetTime = it.endAt,
                            now = now,
                        )
                    } ?: RemainingTimeType.Gone

                    emit(
                        Mutation.NotifyRemainingTime(
                            weeklyRemainingTimeType,
                            dailyRemainingTimeType,
                            welcomeRemainingTimeType,
                        ),
                    )
                }
                delay(RewardPrizeDetailReactor.TICKER_INTERVAL)
            }
        }

    sealed class Mutation : AbstractReactor.Mutation {
        data object SetLoading : Mutation()
        data class SetRefreshing(val isRefreshing: Boolean) : Mutation()
        data object SetError : Mutation()
        data class SetLoaded(
            val dailyRouletteStatus: DailyRouletteStatus,
            val currentKyashCoin: KyashCoin,
            val uncheckRewardCount: Int,
            val pointBalance: PointBalance,
            val spotlightState: SpotlightUiState,
        ) : Mutation()

        data class LoadPrizes(
            val weeklyPrizes: PrizeList,
            val dailyPrizes: PrizeList,
            val weeklyUpcomingPrizes: PrizeList,
            val welcomePrizes: PrizeList,
        ) : Mutation()

        data class RefreshStatus(
            val dailyRouletteStatus: DailyRouletteStatus,
            val currentKyashCoin: KyashCoin,
            val uncheckRewardCount: Int,
            val pointBalance: PointBalance,
        ) : Mutation()

        data class NotifyRemainingTime(
            val weeklyRemainingTimeType: RemainingTimeType,
            val dailyRemainingTimeType: RemainingTimeType,
            val welcomeRemainingTimeType: RemainingTimeType,
        ) : Mutation()

        data class SetTickerEnabled(val isTickerEnabled: Boolean) : Mutation()

        data class SetSpotlightState(val spotlightState: SpotlightUiState) : Mutation()
    }

    data class State(
        val dailyRouletteStatus: DailyRouletteStatus,
        val kyashCoinAmount: Long,
        val weeklyPrizes: List<PrizeUiState>,
        val dailyPrizes: List<PrizeUiState>,
        val weeklyUpcomingPrizes: List<PrizeUiState>,
        val welcomePrizes: List<PrizeUiState>,
        val isRefreshing: Boolean,
        val weeklyRemainingTimeType: RemainingTimeType = RemainingTimeType.Gone,
        val dailyRemainingTimeType: RemainingTimeType = RemainingTimeType.Gone,
        val welcomeRemainingTimeType: RemainingTimeType = RemainingTimeType.Gone,
        val pointBalance: PointBalance,
        internal val spotlightState: SpotlightUiState = SpotlightUiState.Hidden,
        internal val uncheckRewardCount: Int,
        internal val isTickerEnabled: Boolean = true,
    ) : Reactor.State {

        val needShowRewardHistoryIndicator: Boolean = uncheckRewardCount > 0
        val rewardHistoryIndicatorCount = uncheckRewardCount

        data class PrizeUiState(
            internal val prize: Prize,
            val kyashCoinAmount: Long,
        ) {
            val applicableStatus: PrizeApplicableStatus = PrizeApplicableStatus(
                RewardChallengeStatus(prize, kyashCoinAmount),
            )

            val prizeId: String = prize.id
            val title: String = prize.title
            val description = prize.description
            val imageUrl = prize.imageUrl
            val entryCoinAmount = prize.entryCoinAmount
            val maxWinners: Prize.MaxWinners = prize.maxWinners
            val prizeType = prize.type
            val endAt = prize.endAt
            val possessionToEntryCoinRatio = prize.getPossessionToEntryCoinRatio(kyashCoinAmount)
            val needShowPossessionToEntryCoinRatio =
                applicableStatus is PrizeApplicableStatus.Applicable ||
                        applicableStatus is PrizeApplicableStatus.Shortage
        }
    }

    enum class SpotlightState {
        Hidden,
        Ready,
        Roulette,
        WelcomeChallenge,
    }

    sealed class Action : Reactor.Action {
        data object LoadInitially : Action()
        data object Refresh : Action()
        data class TapPrize(val prize: State.PrizeUiState) : Action()
        data object TapRoulette : Action()
        data object TapAppliedPrizeList : Action()
        data object RefreshPrizes : Action()
        data object OnResume : Action()
        data object OnStop : Action()
        data object TapAboutReward : Action()
        data object TapKyashCoinDetail : Action()
        data object TapChallengeStamp : Action()
        data object TapPointDetail : Action()
        data object TapSpotlightScrim : Action()
        data object TapOfferWall : Action()
    }

    sealed class Event : Reactor.Event {
        data class NavigateToPrizeDetail(val prize: Prize) : Event()
        data object NavigateToDailyRoulette : Event()
        data object NavigateToAppliedPrizeList : Event()
        data class HandleFatalError(val error: ApiFatalError) : Event()
        data class ShowError(val error: NonFatalError) : Event()
        data class ShowUncheckedPendingApplicationResult(val prize: UncheckedPrizeSummary) : Event()
        data object NavigateToAboutReward : Event()
        data object ShowRewardOnboarding : Event()
        data object NavigateToKyashCoinDetail : Event()
        data class ShowStampCardDetail(
            val stampCard: StampCardUiState,
        ) : Event()

        data object NavigateToPointHistory : Event()

        data object NavigateToOfferWall : Event()

        data object ShowStampCardOnboarding : Event()

        sealed class ShowSpotlight : Event() {
            data object Roulette : ShowSpotlight()
            data object WelcomeChallenge : ShowSpotlight()
        }
    }
}

private fun PrizeList.toUiStates(availableAmount: Long): List<State.PrizeUiState> {
    return prizes.map { prize ->
        State.PrizeUiState(
            prize = prize,
            kyashCoinAmount = availableAmount,
        )
    }
}

sealed class SpotlightUiState {
    fun completed(): SpotlightUiState = Hidden

    data object Hidden : SpotlightUiState()

    sealed class Ready : SpotlightUiState() {
        abstract fun show(): SpotlightUiState

        data object Roulette : Ready() {
            override fun show(): SpotlightUiState = Show.Roulette
        }

        data object WelcomeChallenge : Ready() {
            override fun show(): SpotlightUiState = Show.WelcomeChallenge
        }
    }

    sealed class Show : SpotlightUiState() {
        abstract fun next(): SpotlightUiState
        abstract fun ready(): SpotlightUiState

        data object Roulette : Show() {
            override fun next(): SpotlightUiState = WelcomeChallenge
            override fun ready(): SpotlightUiState = Ready.WelcomeChallenge
        }

        data object WelcomeChallenge : Show() {
            override fun next(): SpotlightUiState = completed()
            override fun ready(): SpotlightUiState = completed()
        }
    }

    companion object {
        val ready = Ready.Roulette
        val hidden = Hidden
    }
}