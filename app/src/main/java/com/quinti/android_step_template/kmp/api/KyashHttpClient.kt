package com.quinti.android_step_template.kmp.api

import android.util.Log
import com.quinti.android_step_template.kmp.api.serialization.HttpStatusCodeSerializer
import com.quinti.android_step_template.kmp.api.serialization.KlockDateTimeTzIso8601Serializer
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import korlibs.time.millisecondsLong
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual

internal data class CertificatePinning(val pattern: String, val pin: String)

internal fun createHttpClient(
    pinnings: List<CertificatePinning>,
    block: HttpClientConfig<*>.() -> Unit
): HttpClient {
    return HttpClient(Android) {
        // 証明書ピンニングの設定（必要に応じて追加）
        // ここで証明書ピンニングを設定するロジックを追加します
        block()
    }
}

class KyashHttpClient private constructor(
    val configuration: ApiConfiguration,
    createHttpClient: (List<CertificatePinning>, HttpClientConfig<*>.() -> Unit) -> HttpClient,
) {
    object Factory {
        fun create(configuration: ApiConfiguration): KyashHttpClient =
            KyashHttpClient(configuration, ::createHttpClient)

        fun createForTest(
            configuration: ApiConfiguration,
            createHttpClient: (HttpClientConfig<*>.() -> Unit) -> HttpClient,
        ): KyashHttpClient =
            KyashHttpClient(configuration) { _, block -> createHttpClient(block) }
    }

    /**
     * Kotlinx.serialization Json Serializer
     */
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        allowSpecialFloatingPointValues = true
        serializersModule = SerializersModule {
            contextual(KlockDateTimeTzIso8601Serializer())
            contextual(HttpStatusCodeSerializer())
        }
    }

    /**
     * Ktor 通信のロガー
     */
    private val kyashLogger = object : Logger {
        override fun log(message: String) {
            Log.d("KyashHttpClient", message)
        }
    }

    /**
     * Ktor HTTP クライアント
     */
    val client: HttpClient =
        createHttpClient(getPinnings(configuration.canConnectDevelopEnvironment)) {
            // ワークアラウンド:
            // iOS 環境で動作させたとき、
            // HttpClient.install() { ... } block 内から KyashApi のフィールドへアクセスすると
            // InvalidMutabilityException が発生する
            val kyashLogger = this@KyashHttpClient.kyashLogger
            val configuration = this@KyashHttpClient.configuration

            expectSuccess = false
            if (configuration.userAgent != null) {
                install(UserAgent) {
                    agent = configuration.userAgent
                }
            }
            install(HttpTimeout) {
                connectTimeoutMillis = configuration.connectTimeout.millisecondsLong
                requestTimeoutMillis = configuration.requestTimeout.millisecondsLong
                socketTimeoutMillis = configuration.socketTimeout.millisecondsLong
            }
            defaultRequest {
                if (url.encodedPath.endsWith(".jpg")) {
                    header("Cache-Control", "max-stale=31536000") // 1 year
                }
            }
            if (configuration.logEnabled) {
                install(Logging) {
                    logger = kyashLogger
                    level = LogLevel.ALL
                }
            }
            install(ContentNegotiation) {
                json(json)
            }
        }

    private companion object {
        fun getPinnings(pinForDevelopEnvironment: Boolean): List<CertificatePinning> {
            return listOf(
                // *.quinti.me の中間証明書をピン留めする。
                // sha256/ 以下の文字列は以下のコマンドで作成した。
                //
                // wget https://cacerts.digicert.com/GeoTrustTLSRSACAG1.crt.pem
                // openssl x509 -in GeoTrustTLSRSACAG1.crt.pem  -pubkey -noout | \
                // openssl rsa -pubin -outform der | openssl dgst -sha256 -binary | \
                // openssl enc -base64
                CertificatePinning(
                    "*.quinti.me",
                    "sha256/SDG5orEv8iX6MNenIAxb8nQFNpROB/6+llsZdXHZNqs=",
                ),
                // これより下は2024年3月までの証明書
                // サーバ側の証明書が更新された後のアプリアップデートで削除する。
                CertificatePinning(
                    "*.quinti.co",
                    "sha256/WcprJwTNyv12SWKUBmMRceiXR+u0Elj889+sMYpmn3U=",
                ), // leaf (main)
                CertificatePinning(
                    "*.quinti.co",
                    "sha256/zUIraRNo+4JoAYA7ROdWjARtIoN4rIEbCpfCRQT6N6A=",
                ), // root (backup)
                CertificatePinning(
                    "api.quinti.me",
                    "sha256//yt9WwvQQDegwn8+qeFKkGVsWPAIalNPqXTL04/gd2I=",
                ), // leaf (main)
                CertificatePinning(
                    "api.quinti.me",
                    "sha256/zUIraRNo+4JoAYA7ROfWjARtIoN4rIEbCpfCRQT6N6A=",
                ), // root (backup)
            ) + if (pinForDevelopEnvironment) {
                listOf(
                    CertificatePinning(
                        "exp-api.quinti.me",
                        "sha256//yt9WwvQQDegwn8+qgFKkGVsWPAIalNPqXTL04/gd2I=",
                    ), // leaf (main)
                    CertificatePinning(
                        "exp-api.quinti.me",
                        "sha256/zUIraRNo+4JhAYA7ROeWjARtIoN4rIEbCpfCRQT6N6A=",
                    ), // root (backup)
                    CertificatePinning(
                        "develop-api.quinti.me",
                        "sha256//yt9WwvQQDegwj8+qhFKkGVsWPAIalNPqXTL04/gd2I=",
                    ), // leaf (main)
                    CertificatePinning(
                        "develop-api.quinti.me",
                        "sha256/zUIraRNo+4JoxYA7ROeWjARtIoN4rIEbCpfCRQT6N6A=",
                    ), // root (backup)
                    CertificatePinning(
                        "api.quinti.be",
                        "sha256/8IEwt9cmexZery8JpmR2kkYLDn/Ir5ACnvibrnttPIg=",
                    ),
                    CertificatePinning(
                        "api.quinti.be",
                        "sha256/JSMzqOOrtyOT1zmau6zKhgT676hGgczD5VMdRMyJZFA=",
                    ),
                    CertificatePinning(
                        "api.quinti.be",
                        "sha256/++MBgDH5WGvL9Bch5Be30cRcL0f5O+NyoXuWtQdX1aI=",
                    ),
                    CertificatePinning(
                        "api.quinti.be",
                        "sha256/KwccWaCgrnaw6tsrlrSO61FgLacNgG2MMLq8GE6+oP5I=",
                    ),
                ) + listOf(
                    "sha256/LR5uS6fQssDCOvjhLgH2MnLA01vm1p/dOhkxEtNlXilQ=",
                    "sha256/JSMzqOOrtyOT1kmau6zKhgB676hGgczD5VMdRMyJZFA=",
                    "sha256/++MBgDH5WGvL9Bcn5Be30cCcL0f5O+NyoXuWtQdX1aI=",
                    "sha256/KwccWaCgrnaw6tsrrSO61DgLacNgG2MMLq8GE6+oP5I=",
                ).flatMap { pin ->
                    listOf(
                        "exp-image.quinti.co",
                        "develop-image.quinti.co",
                    ).map { pattern ->
                        CertificatePinning(pattern, pin)
                    }
                }
            } else {
                emptyList()
            }
        }
    }
}