package com.quinti.android_step_template.kmp.data.api

import com.quinti.android_step_template.kmp.data.api.exception.ApiErrorResponseException
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalError
import com.quinti.android_step_template.kmp.data.api.exception.ApiFatalErrorException
import com.quinti.android_step_template.kmp.data.api.inject.Provider
import com.quinti.android_step_template.kmp.data.api.inject.SingletonProvider
import com.quinti.android_step_template.kmp.data.api.response.base.ErrorResponse
import com.quinti.android_step_template.kmp.exception.UnknownException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.JsonObjectBuilder
import kotlinx.serialization.json.buildJsonObject
import java.io.IOException


/**
 * API の基本処理
 */
abstract class KyashApi(
    private val httpClientProvider: SingletonProvider<KyashHttpClient>,
    private val endpointProvider: Provider<ApiEndpoint>,
    private val sessionProvider: Provider<ApiSession>,
) {
    val client: HttpClient
        get() = httpClientProvider.get().client

    protected fun apiUrl(path: String): String =
        "${endpointProvider.get().endpoint.removeSuffix("/")}$path"

    /**
     * GET リクエストを送信する
     *
     * @see [API → Reactor のデータフローのエラーについて、 throw と Result.failure が混在しているが、統一する。](https://github.com/Kyash/Kyash-Mobile-Module/discussions/795)
     */
    @Deprecated(
        "新規に作成する API クライアントは getV2 を使ってください。",
    )
    suspend inline fun <reified T> get(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): Result<T> = wrapResult {
        api { headers ->
            val response = client.get(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * GET リクエストを送信する。
     */
    suspend inline fun <reified T> getV2(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): T = tryCatch {
        api { headers ->
            val response = client.get(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * POST リクエストを送信する
     *
     * @see [API → Reactor のデータフローのエラーについて、 throw と Result.failure が混在しているが、統一する。](https://github.com/Kyash/Kyash-Mobile-Module/discussions/795)
     */
    @Deprecated(
        "新規に作成する API クライアントは postV2 を使ってください。",
    )
    suspend inline fun <reified T> post(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): Result<T> = wrapResult {
        api { headers ->
            val response = client.post(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * POST リクエストを送信する
     */
    suspend inline fun <reified T> postV2(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): T = tryCatch {
        api { headers ->
            val response = client.post(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * PUT リクエストを送信する
     *
     * @see [API → Reactor のデータフローのエラーについて、 throw と Result.failure が混在しているが、統一する。](https://github.com/Kyash/Kyash-Mobile-Module/discussions/795)
     */
    @Deprecated(
        "新規に作成する API クライアントは putV2 を使ってください。",
    )
    suspend inline fun <reified T> put(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): Result<T> = wrapResult {
        api { headers ->
            val response = client.put(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * PUT リクエストを送信する。
     */
    suspend inline fun <reified T> putV2(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): T = tryCatch {
        api { headers ->
            val response = client.put(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * PATCH リクエストを送信する。
     */
    suspend inline fun <reified T> patch(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): T = tryCatch {
        api { headers ->
            val response = client.patch(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * DELETE リクエストを送信する。
     */
    suspend inline fun <reified T> deleteV2(
        url: String,
        crossinline block: HttpRequestBuilder.() -> Unit,
    ): T = tryCatch {
        api { headers ->
            val response = client.delete(url) {
                addHeaders(headers)
                block()
            }
            return@api parse<T>(response)
        }
    }

    /**
     * リクエストボディに [body] を JSON として設定する
     */
    inline fun <reified T : Any> HttpRequestBuilder.jsonBody(body: T) {
        contentType(ContentType.Application.Json)
        setBody(body)
    }

    /**
     * リクエストボディに [block] で構築した JSON を設定する
     */
    fun HttpRequestBuilder.jsonBody(block: JsonObjectBuilder.() -> Unit) {
        contentType(ContentType.Application.Json)
        setBody(buildJsonObject(block))
    }

    /**
     * API 呼び出し block を実行し、例外が発生したら
     * - [co.kyash.mobile.core.exception.IOException]
     * - [co.kyash.mobile.core.exception.UnknownException]
     * を投げる
     */
    suspend fun <T> api(
        block: suspend (headers: Map<String, String>) -> T,
    ): T {
        val (endpoint, session) = withContext(Dispatchers.Default) {
            endpointProvider.get() to sessionProvider.get()
        }
        val headers = apiHeaders(
            httpClientProvider.get().configuration,
            endpoint,
            session,
        )
        try {
            return block(headers)
        } catch (e: io.ktor.utils.io.errors.IOException) {
            throw IOException(e)
        } catch (t: Throwable) {
            throw t
        }
    }

    /**
     * block を実行し、結果を Result に変換して返す。ただし、
     * - [UnknownException]
     * - [ApiFatalErrorException]
     * は throw する
     */
    inline fun <reified T> wrapResult(
        block: () -> T,
    ): Result<T> {
        return try {
            Result.success(block())
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: ApiErrorResponseException) {
            Result.failure(e)
        } catch (e: ApiFatalErrorException) {
            throw e
        } catch (t: Throwable) {
            if (t !is UnknownException) {
                throw UnknownException(t)
            }
            throw t
        }
    }

    inline fun <reified T> tryCatch(block: () -> T): T {
        return try {
            block()
        } catch (e: IOException) {
            throw e
        } catch (e: ApiErrorResponseException) {
            throw e
        } catch (e: ApiFatalErrorException) {
            throw e
        } catch (t: Throwable) {
            if (t !is UnknownException) {
                throw UnknownException(t)
            }
            throw t
        }
    }

    /**
     * レスポンスをパースし、エラーレスポンスに対しては
     * - [ApiFatalErrorException]
     * - [ApiErrorResponseException]
     * のいずれかを投げる
     */
    suspend inline fun <reified T> parse(
        response: HttpResponse,
    ): T =
        if (response.status.isSuccess()) {
            response.body()
        } else {
            val errorResponse = runCatching { response.body<ErrorResponse>() }.getOrNull()
            if (errorResponse != null) {
                ApiFatalError.from(response.status, errorResponse)?.let { fatalError ->
                    throw ApiFatalErrorException(fatalError)
                }
            }

            throw ApiErrorResponseException(
                response.status,
                errorResponse,
            )
        }

    companion object {
        fun HttpRequestBuilder.addHeaders(headers: Map<String, String>) {
            headers.forEach { (key, value) ->
                header(key, value)
            }
        }
    }
}