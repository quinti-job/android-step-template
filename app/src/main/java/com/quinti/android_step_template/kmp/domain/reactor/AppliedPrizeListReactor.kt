package com.quinti.android_step_template.kmp.domain.reactor

import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.kmp.data.api.exception.asNonFatalError
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.entity.Prize
import com.quinti.android_step_template.kmp.data.entity.PrizeApplicationResult
import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.Action
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.Event
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.Mutation
import com.quinti.android_step_template.kmp.domain.reactor.AppliedPrizeListReactor.State
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.isLoaded
import com.quinti.android_step_template.kmp.domain.reactor.base.transformIfLoaded
import com.quinti.android_step_template.kmp.domain.usecase.GetUncheckRewardCountUseCase
import com.quinti.android_step_template.kmp.domain.usecase.QueryAppliedDailyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.QueryAppliedWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshAppliedDailyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.RefreshAppliedWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchAppliedDailyPrizesUseCase
import com.quinti.android_step_template.kmp.domain.usecase.WatchAppliedWeeklyPrizesUseCase
import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge

@Suppress("LongParameterList")
class AppliedPrizeListReactor(
    mainDispatcher: CoroutineDispatcher,
    private val watchAppliedWeeklyPrizes: WatchAppliedWeeklyPrizesUseCase,
    private val watchAppliedDailyPrizes: WatchAppliedDailyPrizesUseCase,
    private val queryAppliedWeeklyPrizes: QueryAppliedWeeklyPrizesUseCase,
    private val queryAppliedDailyPrizes: QueryAppliedDailyPrizesUseCase,
    private val refreshAppliedWeeklyPrizes: RefreshAppliedWeeklyPrizesUseCase,
    private val refreshAppliedDailyPrizes: RefreshAppliedDailyPrizesUseCase,
    private val getUncheckRewardCountUseCase: GetUncheckRewardCountUseCase,
) : AbstractReactor<Reactor.LoadState<State>, Action, Mutation, Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {

    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> loadInitially()
            is Action.TapAppliedPrize -> tapAppliedPrize(action.prize.prize)
            Action.TapNavigation -> notify(Event.Back)
            Action.Refresh -> refresh()
            Action.RefreshStatus -> refreshStatus()
            Action.LoadNext.Daily -> loadDailyNext()
            Action.LoadNext.Weekly -> loadWeeklyNext()
            is Action.ChangeFilter -> {
                emit(
                    Mutation.SetFilter(
                        appliedPrizeFilter = action.appliedPrizeFilter,
                        prizeType = action.prizeType,
                    ),
                )
                execute(
                    Action.RefreshTab(
                        prizeType = action.prizeType,
                    ),
                )
            }

            is Action.RefreshTab -> refresh(
                action.prizeType,
            )
        }
    }

    @Suppress("CyclomaticComplexMethod", "LongMethod")
    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            Mutation.SetLoading -> Reactor.LoadState.Loading()
            is Mutation.SetLoaded -> Reactor.LoadState.Loaded(
                State(
                    weeklyTab = State.Tab(
                        uncheckedCount = mutation.weeklyPrizeUncheckedCount,
                        prizeType = Prize.Type.Weekly,
                        prizeList = State.LoadableList.Loading,
                        hasMore = false,
                        filter = AppliedPrizeFilter.All,
                    ),
                    dailyTab = State.Tab(
                        uncheckedCount = mutation.dailyPrizeUncheckedCount,
                        prizeType = Prize.Type.Daily,
                        prizeList = State.LoadableList.Loading,
                        hasMore = false,
                        filter = AppliedPrizeFilter.All,
                    ),
                ),
            )

            is Mutation.SetUncheckedCount -> state.transformIfLoaded {
                it.copy(
                    weeklyTab = it.weeklyTab.copy(
                        uncheckedCount = mutation.weeklyPrizeUncheckedCount,
                    ),
                    dailyTab = it.dailyTab.copy(
                        uncheckedCount = mutation.dailyPrizeUncheckedCount,
                    ),
                )
            }

            is Mutation.SetPrizes -> state.transformIfLoaded {
                it.copy(
                    weeklyTab = it.weeklyTab.copy(
                        prizeList = State.LoadableList.Loaded(
                            mutation.weeklyPrizeList.prizes.map { prize ->
                                State.AppliedPrizeItem(prize)
                            },
                        ),
                        hasMore = mutation.weeklyPrizeList.hasNext,
                    ),
                    dailyTab = it.dailyTab.copy(
                        prizeList = State.LoadableList.Loaded(
                            mutation.dailyPrizeList.prizes.map { prize ->
                                State.AppliedPrizeItem(prize)
                            },
                        ),
                        hasMore = mutation.dailyPrizeList.hasNext,
                    ),
                    isRefreshing = false,
                )
            }

            Mutation.SetLoadError -> Reactor.LoadState.Error()
            Mutation.SetRefreshing -> state.transformIfLoaded {
                it.copy(isRefreshing = true)
            }

            is Mutation.SetWeeklyAdditionalItems -> state.transformIfLoaded { currentState ->
                val weeklyList = when (val loadableList = currentState.weeklyTab.prizeList) {
                    is State.LoadableList.Loaded -> State.LoadableList.Loaded(
                        loadableList.list + mutation.prizeList.map {
                            State.AppliedPrizeItem(it)
                        },
                    )

                    State.LoadableList.Loading -> State.LoadableList.Loading
                }
                currentState.copy(
                    weeklyTab = currentState.weeklyTab.copy(
                        prizeList = weeklyList,
                        hasMore = mutation.hasNext,
                    ),
                )
            }

            is Mutation.SetDailyAdditionalItems -> state.transformIfLoaded { currentState ->
                val dailyList = when (val loadableList = currentState.dailyTab.prizeList) {
                    is State.LoadableList.Loaded -> State.LoadableList.Loaded(
                        loadableList.list + mutation.prizeList.map {
                            State.AppliedPrizeItem(it)
                        },
                    )

                    State.LoadableList.Loading -> State.LoadableList.Loading
                }
                currentState.copy(
                    dailyTab = currentState.dailyTab.copy(
                        prizeList = dailyList,
                        hasMore = mutation.hasNext,
                    ),
                )
            }

            is Mutation.SetFilter -> state.transformIfLoaded {
                when (mutation.prizeType) {
                    Prize.Type.Weekly -> it.copy(
                        weeklyTab = it.weeklyTab.copy(
                            filter = mutation.appliedPrizeFilter,
                        ),
                    )

                    Prize.Type.Daily -> it.copy(
                        dailyTab = it.dailyTab.copy(
                            filter = mutation.appliedPrizeFilter,
                        ),
                    )

                    Prize.Type.Welcome -> it
                }
            }
        }
    }

    // loadInitially
    private suspend fun FlowCollector<Mutation>.loadInitially() {
        runCatching {
            coroutineScope {
                // 未確認の懸賞数を先に取得する
                val weeklyPrizeUncheckedCountDeferred = async { getUncheckRewardCountUseCase() }

                emit(
                    Mutation.SetLoaded(
                        weeklyPrizeUncheckedCount = weeklyPrizeUncheckedCountDeferred.await(),
                        dailyPrizeUncheckedCount = 0,
                    ),
                )

                val weeklyDeferred = async {
                    refreshAppliedWeeklyPrizes(
                        AppliedPrizeFilter.All,
                    )
                }
                val dailyDeferred = async {
                    refreshAppliedDailyPrizes(
                        AppliedPrizeFilter.All,
                    )
                }
                awaitAll(weeklyDeferred, dailyDeferred)
            }
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
            emit(Mutation.SetLoadError)
        }
    }

    // refreshStatus
    private suspend fun FlowCollector<Mutation>.refreshStatus() {
        if (!state.value.isLoaded) return

        runCatching {
            coroutineScope {
                val weeklyPrizeUncheckedCountDeferred = async { getUncheckRewardCountUseCase() }
                emit(
                    Mutation.SetUncheckedCount(
                        weeklyPrizeUncheckedCount = weeklyPrizeUncheckedCountDeferred.await(),
                        dailyPrizeUncheckedCount = 0,
                    ),
                )
            }
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
            emit(Mutation.SetLoadError)
        }
    }

    // refresh
    private suspend fun FlowCollector<Mutation>.refresh() {
        emit(Mutation.SetRefreshing)
        val currentState = (state.value as? Reactor.LoadState.Loaded<State>)?.value ?: return
        runCatching {
            coroutineScope {
                val weeklyPrizeUncheckedCount = getUncheckRewardCountUseCase()
                emit(
                    Mutation.SetUncheckedCount(
                        weeklyPrizeUncheckedCount = weeklyPrizeUncheckedCount,
                        dailyPrizeUncheckedCount = 0,
                    ),
                )

                val weeklyDeferred = async {
                    refreshAppliedWeeklyPrizes(
                        filter = currentState.weeklyTab.filter,
                    )
                }
                val dailyDeferred = async {
                    refreshAppliedDailyPrizes(
                        filter = currentState.dailyTab.filter,
                    )
                }
                awaitAll(weeklyDeferred, dailyDeferred)
            }
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
            emit(Mutation.SetLoadError)
        }
    }

    private suspend fun FlowCollector<Mutation>.refresh(
        prizeType: Prize.Type,
    ) {
        emit(Mutation.SetRefreshing)
        val currentState = (state.value as? Reactor.LoadState.Loaded<State>)?.value ?: return
        runCatching {
            coroutineScope {
                val weeklyPrizeUncheckedCount = getUncheckRewardCountUseCase()
                emit(
                    Mutation.SetUncheckedCount(
                        weeklyPrizeUncheckedCount = weeklyPrizeUncheckedCount,
                        dailyPrizeUncheckedCount = 0,
                    ),
                )
                when (prizeType) {
                    Prize.Type.Weekly -> refreshAppliedWeeklyPrizes(
                        filter = currentState.weeklyTab.filter,
                    )
                    Prize.Type.Daily -> {
                        refreshAppliedDailyPrizes(
                            filter = currentState.dailyTab.filter,
                        )
                    }
                    Prize.Type.Welcome -> { }
                }
            }
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
            emit(Mutation.SetLoadError)
        }
    }

    private suspend fun FlowCollector<Mutation>.loadWeeklyNext() {
        val currentState = (state.value as? Reactor.LoadState.Loaded<State>)?.value ?: return
        runCatching {
            queryAppliedWeeklyPrizes(currentState.weeklyTab.filter)
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
        }
    }

    private suspend fun FlowCollector<Mutation>.loadDailyNext() {
        val currentState = (state.value as? Reactor.LoadState.Loaded<State>)?.value ?: return
        runCatching {
            queryAppliedDailyPrizes(currentState.weeklyTab.filter)
        }.onFailure {
            when (it) {
                is ApiFatalErrorException -> notify(Event.HandleFatalError(it.error))
                else -> notify(Event.ShowError(it.asNonFatalError()))
            }
        }
    }

    /**
     * 懸賞結果によって抽選結果ダイアログを表示するか詳細に遷移するかを判定する。
     * この画面では、ウィークリーチャレンジで発表済み、かつ、未確認の場合のみ抽選結果ダイアログを表示する。
     *
     * 条件
     * ・ウィークリーチャレンジ以外 → 詳細へ遷移
     * ・結果がまだ出ていない（Pending）の場合 → 詳細へ遷移
     * ・結果を確認済み → 詳細へ遷移
     * ・結果を未確認 → 抽選結果ダイアログを表示
     *
     * @param prize 懸賞
     */
    private fun tapAppliedPrize(prize: Prize) {
        if (prize.type == Prize.Type.Daily) {
            notify(Event.NavigateToPrizeDetail(prize))
            return
        }

        if (prize.lotteryResultChecked) {
            notify(Event.NavigateToPrizeDetail(prize))
            return
        }

        val result = PrizeApplicationResult.getOrNull(prize)
        if (result == null || result is PrizeApplicationResult.Pending) {
            notify(Event.NavigateToPrizeDetail(prize))
        } else {
            notify(Event.ShowApplicationResult(result))
        }
    }

    override fun transformMutation(mutationFlow: Flow<Mutation>): Flow<Mutation> {
        return super.transformMutation(
            merge(
                mutationFlow,
                prizesFlow,
            ),
        )
    }

    private val prizesFlow: Flow<Mutation>
        get() = combine(
            watchAppliedWeeklyPrizes(),
            watchAppliedDailyPrizes(),
            state.map { it.isLoaded }.distinctUntilChanged(),
        ) { weekly, daily, isLoaded ->
            if (isLoaded) {
                Mutation.SetPrizes(
                    weeklyPrizeList = weekly,
                    dailyPrizeList = daily,
                )
            } else {
                null
            }
        }.filterNotNull()

    data class State(
        val weeklyTab: Tab,
        val dailyTab: Tab,
        val isRefreshing: Boolean = false,
    ) : Reactor.State {
        data class AppliedPrizeItem(
            internal val prize: Prize,
        ) {
            val prizeId = prize.id
            val type = prize.type
            val title = prize.title
            val imageUrl = prize.imageUrl
            val unchecked = prize.isLotteryResultNotChecked
            val entryCoinAmount = prize.entryCoinAmount
            val displayType = AppliedPrizeStatus(prize)
        }

        data class Tab(
            val prizeList: LoadableList,
            val prizeType: Prize.Type,
            val hasMore: Boolean,
            val uncheckedCount: Int,
            val filter: AppliedPrizeFilter,
        ) {
            val showTabIndicator: Boolean =
                prizeType == Prize.Type.Weekly && uncheckedCount > 0
        }

        sealed class LoadableList {
            data object Loading : LoadableList()
            data class Loaded(val list: List<AppliedPrizeItem>) : LoadableList()
        }
    }

    sealed class Mutation : AbstractReactor.Mutation {

        data class SetLoaded(
            val weeklyPrizeUncheckedCount: Int,
            val dailyPrizeUncheckedCount: Int,
        ) : Mutation()

        data class SetUncheckedCount(
            val weeklyPrizeUncheckedCount: Int,
            val dailyPrizeUncheckedCount: Int,
        ) : Mutation()

        data class SetPrizes(
            val weeklyPrizeList: PrizeList,
            val dailyPrizeList: PrizeList,
        ) : Mutation()

        data class SetWeeklyAdditionalItems(
            val prizeList: List<Prize>,
            val hasNext: Boolean,
        ) : Mutation()

        data class SetDailyAdditionalItems(
            val prizeList: List<Prize>,
            val hasNext: Boolean,
        ) : Mutation()

        data object SetLoading : Mutation()
        data object SetLoadError : Mutation()
        data object SetRefreshing : Mutation()

        data class SetFilter(
            val appliedPrizeFilter: AppliedPrizeFilter,
            val prizeType: Prize.Type,
        ) : Mutation()
    }

    sealed class Action : Reactor.Action {
        data object LoadInitially : Action()
        data object TapNavigation : Action()
        data class TapAppliedPrize(val prize: State.AppliedPrizeItem) : Action()
        data object Refresh : Action()

        data class RefreshTab(
            val prizeType: Prize.Type,
        ) : Action()

        data object RefreshStatus : Action()

        sealed class LoadNext : Action() {
            data object Weekly : LoadNext()
            data object Daily : LoadNext()
        }

        data class ChangeFilter(
            val appliedPrizeFilter: AppliedPrizeFilter,
            val prizeType: Prize.Type,
        ) : Action()
    }

    sealed class Event : Reactor.Event {
        data object Back : Event()
        data class NavigateToPrizeDetail(val prize: Prize) : Event()
        data class ShowApplicationResult(val result: PrizeApplicationResult) : Event()
        data class HandleFatalError(val error: ApiFatalError) : Event()
        data class ShowError(val error: NonFatalError) : Event()
    }
}