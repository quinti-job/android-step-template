package com.quinti.android_step_template.kmp.api.di

import com.quinti.android_step_template.kmp.api.ApiEndpoint
import com.quinti.android_step_template.kmp.api.ApiSession
import com.quinti.android_step_template.kmp.api.KyashHttpClient
import com.quinti.android_step_template.kmp.api.inject.Provider
import com.quinti.android_step_template.kmp.api.inject.SingletonProvider

interface KyashApiFactory {
    val httpClient: SingletonProvider<KyashHttpClient>
    val endpoint: Provider<ApiEndpoint>
    val session: Provider<ApiSession>
}