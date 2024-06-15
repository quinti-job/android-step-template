package com.quinti.android_step_template.kmp.domain.reactor

import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError
import com.quinti.android_step_template.kmp.data.api.exception.asNonFatalError
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor.State
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor.Action
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor.Mutation
import com.quinti.android_step_template.kmp.domain.reactor.DailyRouletteReactor.Event
import com.quinti.android_step_template.kmp.domain.reactor.base.AbstractReactor
import com.quinti.android_step_template.kmp.domain.reactor.base.Reactor
import com.quinti.android_step_template.kmp.domain.reactor.base.transformIfLoaded
import com.quinti.android_step_template.kmp.domain.usecase.GetDailyRouletteOptionsUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetDailyRouletteResultUseCase
import com.quinti.android_step_template.kmp.domain.usecase.GetDailyRouletteStatusUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.internal.NopCollector.emit
import okhttp3.internal.notify

class DailyRouletteReactor(
    mainDispatcher: CoroutineDispatcher,
    private val getDailyRouletteResult: GetDailyRouletteResultUseCase,
    private val getDailyRouletteStatus: GetDailyRouletteStatusUseCase,
    private val getDailyRouletteOptions: GetDailyRouletteOptionsUseCase,
) : AbstractReactor<Reactor.LoadState<State>, Action, Mutation, Event>(
    mainDispatcher = mainDispatcher,
    initialState = Reactor.LoadState.Loading(),
) {
    override fun mutate(action: Action): Flow<Mutation> = flow {
        when (action) {
            Action.LoadInitially -> loadInitially()
            Action.TapApply -> apply(state.value)
            Action.TapClose -> notify(Event.Close)
            Action.TapNavigation -> notify(Event.Close)
            Action.RouletteAnimationCompleted -> emit(Mutation.SetCompleted)
        }
    }

    override fun reduce(
        state: Reactor.LoadState<State>,
        mutation: Mutation,
    ): Reactor.LoadState<State> {
        return when (mutation) {
            is Mutation.SetChallenged ->
                Reactor.LoadState.Loaded(State.challenged(mutation.value.coinAmounts))

            is Mutation.SetLoaded ->
                Reactor.LoadState.Loaded(State.initialized(mutation.value.coinAmounts))

            Mutation.SetReadyStart -> state.transformIfLoaded {
                if (it is State.Initialized) {
                    it.copy(actionButtonEnabled = false)
                } else {
                    error("Invalid state")
                }
            }

            is Mutation.StartRoulette -> state.transformIfLoaded {
                if (it is State.Initialized) {
                    it.toRouletteStarted(mutation.value)
                } else {
                    error("Invalid state")
                }
            }

            Mutation.SetCompleted -> state.transformIfLoaded {
                if (it is State.RouletteStarted) {
                    it.toCompleted()
                } else {
                    error("Invalid state")
                }
            }

            Mutation.SetError -> Reactor.LoadState.Error()
        }
    }

    private suspend fun FlowCollector<Mutation>.loadInitially() {
        runCatching {
            coroutineScope {
                val optionsDeferred = async { getDailyRouletteOptions() }
                val statusDeferred = async { getDailyRouletteStatus() }

                val status = statusDeferred.await()
                if (status is DailyRouletteStatus.Unavailable) {
                    emit(Mutation.SetError)
                    return@coroutineScope
                }

                val options = optionsDeferred.await()
                if (status is DailyRouletteStatus.Done) {
                    emit(Mutation.SetChallenged(options))
                    return@coroutineScope
                }

                emit(Mutation.SetLoaded(options))
            }
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
            emit(Mutation.SetError)
        }
    }

    private suspend fun FlowCollector<Mutation>.apply(state: Reactor.LoadState<State>) {
        if (state !is Reactor.LoadState.Loaded<State>) return

        emit(Mutation.SetReadyStart)

        runCatching {
            val result = getDailyRouletteResult()

            // ルーレット開始
            emit(Mutation.StartRoulette(result))
        }.onFailure { throwable ->
            if (throwable is ApiFatalErrorException) {
                notify(Event.HandleFatalError(throwable.error))
            } else {
                notify(Event.ShowError(throwable.asNonFatalError()))
            }
        }
    }

    sealed class Mutation : AbstractReactor.Mutation {
        data class SetLoaded(
            val value: DailyRouletteOptions,
        ) : Mutation()

        data object SetReadyStart : Mutation()

        data class StartRoulette(
            val value: Long,
        ) : Mutation()

        data object SetCompleted : Mutation()

        data class SetChallenged(
            val value: DailyRouletteOptions,
        ) : Mutation()

        data object SetError : Mutation()
    }

    sealed class State : Reactor.State {

        /**
         * ルーレットボタン
         */
        abstract val actionButtonType: ActionButtonType
        abstract val actionButtonVisibility: Boolean
        abstract val actionButtonEnabled: Boolean

        /**
         * 上部ラベル
         */
        abstract val messageLabelType: MessageLabelType
        abstract val messageLabelVisibility: Boolean

        /**
         * ルーレットの状態
         */
        abstract val rouletteState: RouletteState

        /**
         * ルーレットの結果表示の状態
         */
        abstract val resultSlotState: RouletteResultSlotState

        data class Initialized(
            val values: List<Long>,
            override val actionButtonEnabled: Boolean = true,
        ) : State() {

            override val actionButtonType: ActionButtonType = ActionButtonType.Playable
            override val actionButtonVisibility: Boolean = true
            override val messageLabelType: MessageLabelType = MessageLabelType.Playable
            override val messageLabelVisibility: Boolean = true
            override val rouletteState: RouletteState = RouletteState.Ready(values)
            override val resultSlotState: RouletteResultSlotState = RouletteResultSlotState.Ready

            internal fun toRouletteStarted(value: Long): State {
                return RouletteStarted(
                    lotteryCandidates = values,
                    lotteryResult = value,
                )
            }
        }

        internal data class RouletteStarted(
            internal val lotteryCandidates: List<Long>,
            internal val lotteryResult: Long,
        ) : State() {

            override val actionButtonEnabled: Boolean = false
            override val actionButtonType: ActionButtonType = ActionButtonType.Playable
            override val actionButtonVisibility: Boolean = false
            override val messageLabelType: MessageLabelType = MessageLabelType.Playable
            override val messageLabelVisibility: Boolean = false
            override val resultSlotState: RouletteResultSlotState =
                RouletteResultSlotState.Start(lotteryResult = lotteryResult)
            override val rouletteState: RouletteState =
                RouletteState.Start(
                    lotteryCandidates = lotteryCandidates,
                    lotteryResult = lotteryResult,
                )

            internal fun toCompleted(): State {
                return Completed(
                    resultSlotState = resultSlotState,
                    rouletteState = rouletteState,
                )
            }
        }

        internal data class Completed(
            override val resultSlotState: RouletteResultSlotState,
            override val rouletteState: RouletteState,
        ) : State() {
            override val actionButtonType: ActionButtonType = ActionButtonType.Close
            override val actionButtonVisibility: Boolean = true
            override val actionButtonEnabled: Boolean = true
            override val messageLabelType: MessageLabelType = MessageLabelType.Determined
            override val messageLabelVisibility: Boolean = true
        }

        internal data class Challenged(
            val values: List<Long>,
        ) : State() {

            override val actionButtonType: ActionButtonType = ActionButtonType.Playable
            override val actionButtonVisibility: Boolean = true
            override val actionButtonEnabled: Boolean = false
            override val messageLabelType: MessageLabelType = MessageLabelType.Challenged
            override val messageLabelVisibility: Boolean = true
            override val rouletteState: RouletteState = RouletteState.Challenged(values)
            override val resultSlotState: RouletteResultSlotState = RouletteResultSlotState.Ready
        }

        enum class ActionButtonType {
            Playable,
            Close,
        }

        enum class MessageLabelType {
            Playable,
            Determined,
            Challenged,
        }

        sealed class RouletteState {

            abstract val lotteryCandidates: List<Long>

            data class Ready(
                override val lotteryCandidates: List<Long>,
            ) : RouletteState()

            data class Start(
                override val lotteryCandidates: List<Long>,
                val lotteryResult: Long,
            ) : RouletteState()

            data class Challenged(
                override val lotteryCandidates: List<Long>,
            ) : RouletteState()
        }

        sealed class RouletteResultSlotState {

            data object Ready : RouletteResultSlotState()
            data class Start(
                val lotteryResult: Long,
            ) : RouletteResultSlotState()
        }

        companion object {
            fun initialized(lotteryCandidates: List<Long>): State = Initialized(lotteryCandidates)
            fun challenged(lotteryCandidates: List<Long>): State = Challenged(lotteryCandidates)
        }
    }

    sealed class Action : Reactor.Action {
        data object LoadInitially : Action()
        data object TapApply : Action()
        data object RouletteAnimationCompleted : Action()
        data object TapNavigation : Action()
        data object TapClose : Action()
    }

    sealed class Event : Reactor.Event {
        data object Close : Event()
        data object ShowProgress : Event()
        data object HideProgress : Event()
        data class HandleFatalError(val error: ApiFatalError) : Event()
        data class ShowError(val error: NonFatalError) : Event()
    }
}