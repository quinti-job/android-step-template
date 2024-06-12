package com.quinti.android_step_template.kmp.domain.reactor.base

import androidx.lifecycle.ViewModel

abstract class ReactorViewModel<T : Reactor<*, *, *>>(
    val reactor: T,
) : ViewModel() {
    override fun onCleared() {
        reactor.destroy()
    }
}