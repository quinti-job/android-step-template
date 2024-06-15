package com.quinti.android_step_template.kmp.data.api.exception

import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError.ConnectionError
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError.MessageError
import com.quinti.android_step_template.kmp.data.api.exception.NonFatalError.UnexpectedData
import java.io.IOException

sealed class NonFatalError {
    data class MessageError(val message: String) : NonFatalError()
    data object ConnectionError : NonFatalError()
    data object UnexpectedData : NonFatalError()
}

fun Throwable.asNonFatalError(): NonFatalError =
    when (this) {
        is ApiErrorResponseException -> {
            val message = response?.error?.message
            if (message != null) {
                MessageError(message)
            } else {
                UnexpectedData
            }
        }

        is IOException -> ConnectionError
        else -> UnexpectedData
    }