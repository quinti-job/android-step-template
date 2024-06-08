package com.quinti.android_step_template.kmp.api

import korlibs.time.TimeSpan
import korlibs.time.seconds

/**
 * API 設定
 */
data class ApiConfiguration(
    /**
     * UserAgent 文字列
     */
    val userAgent: String? = null,
    /**
     * 通信全体のタイムアウト
     */
    val requestTimeout: TimeSpan = 60.seconds,
    /**
     * コネクション接続確立までのタイムアウト (iOSでは未サポート)
     */
    val connectTimeout: TimeSpan = 20.seconds,
    /**
     * 通信パケット間のタイムアウト (読み取り / 書き込みのタイムアウト)
     */
    val socketTimeout: TimeSpan = 60.seconds,
    /**
     * ログ出力するか
     */
    val logEnabled: Boolean = false,
    /**
     * 開発環境に接続可能か
     */
    val canConnectDevelopEnvironment: Boolean = false,
)