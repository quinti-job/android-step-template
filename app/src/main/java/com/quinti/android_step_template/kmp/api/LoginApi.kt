package com.quinti.android_step_template.kmp.api

import com.quinti.android_step_template.kmp.api.inject.Provider
import com.quinti.android_step_template.kmp.api.inject.SingletonProvider
import com.quinti.android_step_template.kmp.api.response.LoginResponse
import com.quinti.android_step_template.kmp.api.response.base.Response

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