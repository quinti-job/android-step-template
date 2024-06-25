package com.quinti.android_step_template.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import kotlinx.coroutines.delay

fun Modifier.clickableWithoutDoubleTap(
    thresholdMills: Long = 500L,
    onClickLabel: String? = null,
    clickEnabled: Boolean = true,
    enableRipple: Boolean = true,
    onClick: () -> Unit,
) = composed {
    var enabled by remember { mutableStateOf(true) }
    LaunchedEffect(key1 = enabled, block = {
        if (enabled) return@LaunchedEffect
        delay(thresholdMills)
        enabled = true
    },)
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = if (enableRipple) rememberRipple() else null,
        onClickLabel = onClickLabel,
        enabled = clickEnabled,
    ) {
        if (enabled) {
            enabled = false
            onClick()
        }
    }
}