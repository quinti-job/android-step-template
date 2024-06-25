package com.quinti.android_step_template.kmp.data.api

import com.quinti.android_step_template.kmp.data.api.base.ApiEndpoint
import com.quinti.android_step_template.kmp.data.api.base.ApiSession
import com.quinti.android_step_template.kmp.data.api.base.KyashApi
import com.quinti.android_step_template.kmp.data.api.base.KyashHttpClient
import com.quinti.android_step_template.kmp.data.api.inject.Provider
import com.quinti.android_step_template.kmp.data.api.inject.SingletonProvider
import com.quinti.android_step_template.kmp.data.api.response.PointBalanceResponse
import com.quinti.android_step_template.kmp.data.api.response.base.Response
import com.quinti.android_step_template.kmp.exception.UnknownException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

class PointApi(
    httpClient: SingletonProvider<KyashHttpClient>,
    endpoint: Provider<ApiEndpoint>,
    session: Provider<ApiSession>,
) : KyashApi(httpClient, endpoint, session) {

    @Throws(CancellationException::class, IOException::class, UnknownException::class)
    suspend fun getPointBalance(): Result<Response<PointBalanceResponse>> {
        return get(apiUrl("/v1/point/balance")) {}
    }
}