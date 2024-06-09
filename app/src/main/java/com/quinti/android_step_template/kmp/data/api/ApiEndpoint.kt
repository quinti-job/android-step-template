package com.quinti.android_step_template.kmp.data.api

/**
 * API Endpoint 接続情報
 */
data class ApiEndpoint(
    /**
     * API URL ("https://..." 形式)
     */
    val endpoint: String,
    /**
     * 認証トークン用のリクエストヘッダ
     */
    val authHeader: String,
)