package com.quinti.android_step_template.kmp.api.response.base

import kotlinx.serialization.Serializable

/**
 * 成功時の共通 API レスポンス
 */
@Serializable
data class Response<T>(
    val code: Int,
    val detailCode: Long,
    val result: Result<T>,
) {
    @Serializable
    data class Result<T>(
        val data: T,
    )
}