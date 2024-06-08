package com.quinti.android_step_template.kmp.api

import korlibs.time.TimeSpan


/**
 * API セッション
 */
data class ApiSession(
    /**
     * セッショントークン
     */
    val token: String?,

    val clientId: String,
    val clientVersion: String,

    val deviceModel: String,
    val deviceLanguage: String?,
    val deviceUptime: TimeSpan,

    val installationId: String,
    /**
     * Android ADID or iOS IDFA
     */
    val advertisingId: String?,

    var sardineSessionKey: String,
)