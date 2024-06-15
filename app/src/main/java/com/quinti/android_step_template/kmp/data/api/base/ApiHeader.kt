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


//package co.kyash.mobile.data.api
//
//internal actual fun platformHeaders(
//    configuration: ApiConfiguration,
//    endpoint: ApiEndpoint,
//    session: ApiSession,
//): Map<String, String> =
//    mutableMapOf<String, String>().apply {
//        put("X-Kyash-OS", "android")
//        session.advertisingId?.let {
//            put("X-Kyash-ADID", it)
//        }
//    }
//

//package co.kyash.mobile.data.api
//
//import io.ktor.client.HttpClient
//import io.ktor.client.HttpClientConfig
//import io.ktor.client.engine.okhttp.OkHttp
//import okhttp3.CertificatePinner
//
//internal actual fun createHttpClient(
//    pinnings: List<CertificatePinning>,
//    block: HttpClientConfig<*>.() -> Unit,
//): HttpClient = HttpClient(OkHttp) {
//    engine {
//        config {
//            val pin = CertificatePinner.Builder()
//                .apply {
//                    pinnings.forEach { (pattern, pin) ->
//                        add(pattern, pin)
//                    }
//                }
//                .build()
//            certificatePinner(pin)
//            followRedirects(true)
//        }
//    }
//    block()
//}

//package co.kyash.mobile.data.api
//
//internal actual fun platformHeaders(
//    configuration: ApiConfiguration,
//    endpoint: ApiEndpoint,
//    session: ApiSession,
//): Map<String, String> =
//    mutableMapOf<String, String>().apply {
//        put("X-Kyash-OS", "iOS")
//        session.advertisingId?.let {
//            put("X-Kyash-IDFA", it)
//        }
//    }

//package co.kyash.mobile.data.api
//
//import io.ktor.client.HttpClient
//import io.ktor.client.HttpClientConfig
//import io.ktor.client.engine.darwin.Darwin
//import io.ktor.client.engine.darwin.certificates.CertificatePinner
//
//internal actual fun createHttpClient(
//    pinnings: List<CertificatePinning>,
//    block: HttpClientConfig<*>.() -> Unit,
//): HttpClient = HttpClient(Darwin) {
//    engine {
//        val pin = CertificatePinner.Builder()
//            .apply {
//                pinnings.forEach { (pattern, pin) ->
//                    add(pattern, pin)
//                }
//            }
//            .build()
//        handleChallenge(pin)
//    }
//    block()
//}

//package co.kyash.mobile.data.api
//
//import korlibs.time.DateTime
//import korlibs.time.millisecondsLong
//
//internal expect fun platformHeaders(
//    configuration: ApiConfiguration,
//    endpoint: ApiEndpoint,
//    session: ApiSession,
//): Map<String, String>
//
//internal fun apiHeaders(
//    configuration: ApiConfiguration,
//    endpoint: ApiEndpoint,
//    session: ApiSession,
//): Map<String, String> =
//    mutableMapOf<String, String>().apply {
//        put("X-Kyash-Date", (DateTime.nowUnixMillisLong() / 1000L).toString())
//        put("X-Kyash-Client-ID", session.clientId)
//        put("X-Kyash-Client-Version", session.clientVersion)
//        put("X-Kyash-Device-Info", session.deviceModel)
//        session.deviceLanguage?.let {
//            put("X-Kyash-Device-Language", it)
//        }
//        put("X-Kyash-Device-Uptime-Mills", session.deviceUptime.millisecondsLong.toString())
//        put("X-Kyash-Installation-ID", session.installationId)
//        put("X-Kyash-Session-Key", session.sardineSessionKey)
//        session.token?.let {
//            put(endpoint.authHeader, it)
//        }
//    } + platformHeaders(configuration, endpoint, session)



