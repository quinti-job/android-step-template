package com.quinti.android_step_template.kmp.data.api.response.base

import kotlinx.serialization.Serializable

/**
 * API 共通エラーレスポンス
 */
@Serializable
data class ErrorResponse(
    /**
     * API エラーコード
     */
    val code: Int,
    /**
     * API エラー詳細コード
     */
    val detailCode: Int,
    /**
     * エラー内容
     */
    val error: Error,
) {
    @Serializable
    data class Error(
        /**
         * エラーメッセージ
         */
        val message: String,
    )
}