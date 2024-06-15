package com.quinti.android_step_template.kmp.data.api

import com.quinti.android_step_template.kmp.data.api.base.ApiEndpoint
import com.quinti.android_step_template.kmp.data.api.base.ApiSession
import com.quinti.android_step_template.kmp.data.api.base.KyashApi
import com.quinti.android_step_template.kmp.data.api.base.KyashHttpClient
import com.quinti.android_step_template.kmp.data.api.inject.Provider
import com.quinti.android_step_template.kmp.data.api.inject.SingletonProvider
import com.quinti.android_step_template.kmp.data.api.response.LoginResponse
import com.quinti.android_step_template.kmp.data.api.response.base.Response

interface LoginApi {
    suspend fun postLogin(
        email: String,
        password: String,
    ): Response<LoginResponse>
}

class LoginApiImpl(
    httpClient: SingletonProvider<KyashHttpClient>,
    endpoint: Provider<ApiEndpoint>,
    session: Provider<ApiSession>,
) : KyashApi(httpClient, endpoint, session), LoginApi {

    override suspend fun postLogin(
        email: String,
        password: String,
    ): Response<LoginResponse> {
        return getV2(apiUrl("/dev/socialnetwork-debug")) {}
    }

}