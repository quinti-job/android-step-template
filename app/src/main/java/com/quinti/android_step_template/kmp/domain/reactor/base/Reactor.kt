package com.quinti.android_step_template.kmp.domain.reactor.base

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface Reactor<StateT : Reactor.State, ActionT : Reactor.Action, EventT : Reactor.Event> {
    val state: StateFlow<StateT>
    val event: SharedFlow<EventT>

    fun execute(action: ActionT)
    fun destroy()

    interface State
    interface Action
    interface Event

    sealed class LoadState<T : State> : State {
        class Loading<T : State> : LoadState<T>()
        data class Loaded<T : State>(val value: T) : LoadState<T>()
        data class Error<T : State>(val message: String? = null) : LoadState<T>()
    }
}

val <T : Reactor.State> Reactor.LoadState<T>.isLoaded: Boolean
    get() = this is Reactor.LoadState.Loaded<T>

fun <T : Reactor.State> Reactor.LoadState<T>.transformIfLoaded(
    function: (T) -> T,
): Reactor.LoadState<T> {
    return when (this) {
        is Reactor.LoadState.Loaded -> Reactor.LoadState.Loaded(function(value))
        else -> this
    }
}

fun <T : Reactor.State> Reactor.LoadState<T>.whenLoaded(
    function: (T) -> Unit,
) {
    return when (val state = this) {
        is Reactor.LoadState.Loaded -> {
            function(state.value)
        }

        else -> Unit
    }
}