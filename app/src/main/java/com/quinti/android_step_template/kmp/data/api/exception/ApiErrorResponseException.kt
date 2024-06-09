package com.quinti.android_step_template.kmp.data.api.exception

import com.quinti.android_step_template.kmp.data.api.response.base.ErrorResponse
import io.ktor.http.*

/**
 * エラーレスポンスを含む API エラー
 */
class ApiErrorResponseException(
    val statusCode: HttpStatusCode,
    val response: ErrorResponse? = null,
) : RuntimeException(response?.error?.message)