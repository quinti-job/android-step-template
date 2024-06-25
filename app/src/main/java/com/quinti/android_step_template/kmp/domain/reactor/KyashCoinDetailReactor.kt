package com.quinti.android_step_template.kmp.domain.reactor

import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor.Action
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor.Event
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor.Mutation
import com.quinti.android_step_template.kmp.domain.reactor.KyashCoinDetailReactor.State
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.kmp.data.api.exception.asNonFatalError
import com.quinti.android_step_template.kmp.data.entity.KyashCoin
import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistory
import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistoryList
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.isLoaded
import com.quinti.android_step_template.kmp.domain.reactor.base.transformIfLoaded
import com.quinti.android_step_template.kmp.domain.usecase.GetKyashCoinAmountUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetKyashCoinHistoryUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow

class KyashCoinDetailReactor(
    mainDispatcher: CoroutineDispatcher,
    private val getKyashCoinAmount: GetKyashCoinAmountUseCase,
    private val getKyashCoinHistoryUseCase: GetKyashCoinHistoryUseCase,
) : AbstractReactor<Reactor.LoadState<State>, Action, Mutation, Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {

    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> loadInitially()
            Action.Refresh -> {
                emit(Mutation.SetRefreshing)
                loadInitially()
            }

            Action.TapBack -> notify(Event.Back)
            Action.TapInformation -> notify(Event.ShowInformation)
            Action.SyncStatus -> refreshStatus()
            Action.LoadMore -> loadMore()
        }
    }

    private suspend fun FlowCollector<Mutation>.loadMore() {
        val currentState = (state.value as? Reactor.LoadState.Loaded<State>)?.value ?: return
        val nextLastCreatedAt = currentState.nextLastCreatedAt ?: return
        runCatching {
            // コイン履歴取得処理
            val history = getKyashCoinHistoryUseCase(nextLastCreatedAt)
            emit(Mutation.AppendHistories(history))
        }.onFailure {
            if (it is ApiFatalErrorException) {
                notify(Event.HandleFatalError(it.error))
            } else {
                notify(Event.ShowError(it.asNonFatalError()))
            }
        }
    }

    private suspend fun FlowCollector<Mutation>.loadInitially() {
        runCatching {
            coroutineScope {
                // Kyashコインの状態を取得
                val coinDeferred = async { getKyashCoinAmount() }

                // コイン履歴取得処理
                val historiesDeferred = async {
                    getKyashCoinHistoryUseCase()
                }

                emit(
                    Mutation.SetLoaded(
                        coin = coinDeferred.await(),
                        history = historiesDeferred.await(),
                    ),
                )
            }
        }.onFailure {
            if (it is ApiFatalErrorException) {
                notify(Event.HandleFatalError(it.error))
            } else {
                notify(Event.ShowError(it.asNonFatalError()))
            }
            emit(Mutation.SetLoadError)
        }
    }

    private suspend fun FlowCollector<Mutation>.refreshStatus() {
        if (!state.value.isLoaded) return
        runCatching {
            coroutineScope {
                // Kyashコインの状態を取得
                val coinDeferred = async { getKyashCoinAmount() }

                emit(
                    Mutation.RefreshStatus(
                        coin = coinDeferred.await(),
                    ),
                )
            }
        }.onFailure {
            if (it is ApiFatalErrorException) {
                notify(Event.HandleFatalError(it.error))
            } else {
                notify(Event.ShowError(it.asNonFatalError()))
            }
        }
    }

    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            Mutation.SetLoadError -> Reactor.LoadState.Error()
            Mutation.SetLoading -> Reactor.LoadState.Loading()
            is Mutation.SetRefreshing -> state.transformIfLoaded {
                it.copy(isRefreshing = true)
            }

            is Mutation.SetLoaded -> Reactor.LoadState.Loaded(
                State(
                    coinStatus = State.CoinStatus(
                        availableCoinAmount = mutation.coin.availableAmount,
                        expiryCoinAmount = mutation.coin.recentExpiry?.amount ?: 0,
                    ),
                    histories = mutation.history.transactions.filterAndMapAvailable(),
                    nextLastCreatedAt = mutation.history.nextLastCreatedAt,
                ),
            )

            is Mutation.RefreshStatus -> state.transformIfLoaded {
                it.copy(
                    coinStatus = State.CoinStatus(
                        availableCoinAmount = mutation.coin.availableAmount,
                        expiryCoinAmount = mutation.coin.recentExpiry?.amount ?: 0,
                    ),
                )
            }

            is Mutation.AppendHistories -> state.transformIfLoaded {
                it.copy(
                    histories = it.histories + mutation.history.transactions
                        .filterAndMapAvailable(),
                    nextLastCreatedAt = mutation.history.nextLastCreatedAt,
                )
            }
        }
    }

    data class State(
        val coinStatus: CoinStatus,
        val histories: List<KyashCoinHistory.Available>,
        internal val nextLastCreatedAt: String?,
        val isRefreshing: Boolean = false,
    ) : Reactor.State {
        val needShowExpiryCoin = coinStatus.expiryCoinAmount > 0
        val hasMore: Boolean = nextLastCreatedAt != null

        data class CoinStatus(
            val availableCoinAmount: Long,
            val expiryCoinAmount: Long,
        )
    }

    sealed class Mutation : AbstractReactor.Mutation {
        data object SetLoading : Mutation()
        data object SetLoadError : Mutation()
        data object SetRefreshing : Mutation()

        data class SetLoaded(
            val coin: KyashCoin,
            val history: KyashCoinHistoryList,
        ) : Mutation()

        data class AppendHistories(
            val history: KyashCoinHistoryList,
        ) : Mutation()

        data class RefreshStatus(
            val coin: KyashCoin,
        ) : Mutation()
    }

    sealed class Action : Reactor.Action {
        data object LoadInitially : Action()
        data object TapBack : Action()
        data object TapInformation : Action()
        data object Refresh : Action()
        data object SyncStatus : Action()
        data object LoadMore : Action()
    }

    sealed class Event : Reactor.Event {
        data object Back : Event()
        data class HandleFatalError(val error: ApiFatalError) : Event()
        data class ShowError(val error: NonFatalError) : Event()
        data object ShowInformation : Event()
    }
}

private fun List<KyashCoinHistory>.filterAndMapAvailable(): List<KyashCoinHistory.Available> {
    return this.mapNotNull {
        it as? KyashCoinHistory.Available
    }
}