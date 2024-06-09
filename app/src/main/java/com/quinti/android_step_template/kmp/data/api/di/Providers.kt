package com.quinti.android_step_template.kmp.data.api.di

import com.quinti.android_step_template.kmp.data.api.ApiEndpoint
import com.quinti.android_step_template.kmp.data.api.ApiSession
import com.quinti.android_step_template.kmp.data.api.KyashHttpClient
import com.quinti.android_step_template.kmp.data.api.inject.Provider
import com.quinti.android_step_template.kmp.data.api.inject.SingletonProvider

interface KyashApiFactory {
    val httpClient: SingletonProvider<KyashHttpClient>
    val endpoint: Provider<ApiEndpoint>
    val session: Provider<ApiSession>
}