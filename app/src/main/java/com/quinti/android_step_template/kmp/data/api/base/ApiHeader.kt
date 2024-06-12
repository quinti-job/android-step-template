package com.quinti.android_step_template.kmp.data.api.base

import korlibs.time.DateTime
import korlibs.time.millisecondsLong

internal fun platformHeaders(
    configuration: ApiConfiguration,
    endpoint: ApiEndpoint,
    session: ApiSession,
): Map<String, String> {
    // Android用のプラットフォーム固有のヘッダーを追加する場合はここに記述
    return mapOf(
        "X-Platform" to "Android",
        "X-Platform-Version" to android.os.Build.VERSION.RELEASE,
        "X-Device-Model" to android.os.Build.MODEL
    )
}

internal fun apiHeaders(
    configuration: ApiConfiguration,
    endpoint: ApiEndpoint,
    session: ApiSession,
): Map<String, String> =
    mutableMapOf<String, String>().apply {
        put("X-Quinti-Date", (DateTime.nowUnixMillisLong() / 1000L).toString())
        put("X-Quinti-Client-ID", session.clientId)
        put("X-Quinti-Client-Version", session.clientVersion)
        put("X-Quinti-Device-Info", session.deviceModel)
        session.deviceLanguage?.let {
            put("X-Quinti-Device-Language", it)
        }
        put("X-Quinti-Device-Uptime-Mills", session.deviceUptime.millisecondsLong.toString())
        put("X-Quinti-Installation-ID", session.installationId)
        put("X-Quinti-Session-Key", session.sardineSessionKey)
        session.token?.let {
            put(endpoint.authHeader, it)
        }
    } + platformHeaders(configuration, endpoint, session)