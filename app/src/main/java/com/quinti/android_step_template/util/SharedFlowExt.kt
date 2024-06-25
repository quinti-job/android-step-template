package com.quinti.android_step_template.util

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

fun <T> SharedFlow<T>.collectWithLifecycle(
    fragment: androidx.fragment.app.Fragment,
    block: (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.STARTED,
) {
    fragment.apply {
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(state) {
                collect {
                    block(it)
                }
            }
        }
    }
}

fun <T> SharedFlow<T>.collectWithLifecycle(
    activity: ComponentActivity,
    block: (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.STARTED,
) {
    activity.apply {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(state) {
                collect {
                    block(it)
                }
            }
        }
    }
}