package com.quinti.android_step_template.kmp.domain.reactor.base

import android.util.Log
import com.quinti.android_step_template.kmp.exception.UnknownException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.CoroutineContext

@OptIn(FlowPreview::class)
abstract class AbstractReactor<
        StateT : Reactor.State,
        ActionT : Reactor.Action,
        MutationT : AbstractReactor.Mutation,
        EventT : Reactor.Event,
        >(
    mainDispatcher: CoroutineDispatcher,
    initialState: StateT,
) : Reactor<StateT, ActionT, EventT> {
    private val scope = ReactorScope(mainDispatcher)
    private val stateSource = MutableStateFlow(initialState)
    private val actionSource = MutableSharedFlow<ActionT>(
        replay = 0,
        extraBufferCapacity = Int.MAX_VALUE,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    private val eventSource = MutableSharedFlow<EventT>(
        replay = 0,
        extraBufferCapacity = 10,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    internal val reactorScope: CoroutineScope get() = scope

    private val isCollectStarted: AtomicBoolean = AtomicBoolean(false)

    final override val state: StateFlow<StateT> by lazy {
        collectIfNeeded()
        stateSource.asStateFlow()
    }

    final override val event: SharedFlow<EventT> by lazy {
        collectIfNeeded()
        eventSource.asSharedFlow()
    }

    /**
     * collect が実行されるようにタイミングを調整するために関数化している。
     *
     * 背景として継承するクラスのインスタンス初期化をするとき、実行順としては親クラスの init が先に実行される。
     * そのため init で collect が実行されてしまうと、各種 transform 関数のオーバーライドで継承クラスの
     * コンストラクタプロパティの値を利用すると実行時エラーが発生する。これを避けるために活用する。
     * ref: https://kotlinlang.org/docs/inheritance.html#derived-class-initialization-order
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectIfNeeded() {
        if (isCollectStarted.compareAndSet(false, true)) {
            reactorScope.launch {
                transformAction(actionSource)
                    .flatMapMerge { mutate(it) }
                    .let { transformMutation(it) }
                    .map { reduce(stateSource.value, it) }
                    .let { transformState(it) }
                    .collect { stateSource.value = it }
            }
        }
    }

    protected abstract fun mutate(action: ActionT): Flow<MutationT>
    protected abstract fun reduce(state: StateT, mutation: MutationT): StateT

    protected open fun transformAction(actionFlow: Flow<ActionT>): Flow<ActionT> =
        actionFlow.onEach { Log.d("Reactor", "action: $it") }

    protected open fun transformMutation(mutationFlow: Flow<MutationT>): Flow<MutationT> =
        mutationFlow.onEach { Log.d("Reactor", "mutation: $it") }

    protected open fun transformState(stateFlow: Flow<StateT>): Flow<StateT> =
        stateFlow.onEach { Log.d("Reactor", "state: $it") }

    protected open fun onDestroy() {}

    protected fun notify(event: EventT) {
        collectIfNeeded()
        reactorScope.launch {
            eventSource.emit(event)
        }
    }

    final override fun execute(action: ActionT) {
        collectIfNeeded()
        reactorScope.launch {
            actionSource.emit(action)
        }
    }

    final override fun destroy() {
        onDestroy()
        scope.onDestroy()
    }

    protected suspend fun <T : Mutation> FlowCollector<T>.safeEmit(value: T) {
        emit(value)
    }

    interface Mutation

    private class ReactorScope(private val mainDispatcher: CoroutineDispatcher) : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = mainDispatcher + job + exceptionHandler

        private val job = SupervisorJob()

        private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Log.e("ReactorScope", "Reactor crashed", throwable)
            throw UnknownException(throwable)
        }

        fun onDestroy() {
            job.cancel()
        }
    }
}