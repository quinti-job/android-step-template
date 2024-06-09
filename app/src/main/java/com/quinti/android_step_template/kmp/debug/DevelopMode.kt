package com.quinti.android_step_template.kmp.debug

import kotlinx.coroutines.delay

interface DevelopModeFactory {
    fun createDevelopMode(): DevelopMode = DevelopMode(useMock = false, mockDelayMillis = 1000L)
}

data class DevelopMode(
    val useMock: Boolean,
    val mockDelayMillis: Long,
)

suspend fun <T> mock(
    developMode: DevelopMode,
    actual: suspend () -> Result<T>,
    mock: () -> T,
): Result<T> =
    if (developMode.useMock) {
        delay(developMode.mockDelayMillis)
        Result.success(mock())
    } else {
        actual()
    }