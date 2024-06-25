package com.quinti.android_step_template.kmp.domain.analytics

import androidx.compose.runtime.staticCompositionLocalOf

val LocalEventTracker = staticCompositionLocalOf<EventTracker> {
    error("LocalEventTracker not provided")
}