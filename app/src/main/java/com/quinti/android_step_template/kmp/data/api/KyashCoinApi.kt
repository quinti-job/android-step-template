package com.quinti.android_step_template.kmp.data.api

import com.quinti.android_step_template.kmp.data.api.inject.Provider
import com.quinti.android_step_template.kmp.data.api.base.ApiEndpoint
import com.quinti.android_step_template.kmp.data.api.base.ApiSession
import com.quinti.android_step_template.kmp.data.api.base.KyashApi
import com.quinti.android_step_template.kmp.data.api.base.KyashHttpClient
import com.quinti.android_step_template.kmp.data.api.inject.SingletonProvider
import com.quinti.android_step_template.kmp.data.api.response.ApplyCoinPrizeResponse
import com.quinti.android_step_template.kmp.data.api.response.CoinPrizeListResponse
import com.quinti.android_step_template.kmp.data.api.response.CoinPrizeResponse
import com.quinti.android_step_template.kmp.data.api.response.CurrentKyashCoinResponse
import com.quinti.android_step_template.kmp.data.api.response.DailyRouletteOptionsResponse
import com.quinti.android_step_template.kmp.data.api.response.DailyRouletteResultResponse
import com.quinti.android_step_template.kmp.data.api.response.DailyRouletteStatusResponse
import com.quinti.android_step_template.kmp.data.api.response.ExchangeStampCardResponse
import com.quinti.android_step_template.kmp.data.api.response.GetRewardOnboardingEventResponse
import com.quinti.android_step_template.kmp.data.api.response.KyashCoinHistoryListResponse
import com.quinti.android_step_template.kmp.data.api.response.ReceivePrizeResponse
import com.quinti.android_step_template.kmp.data.api.response.StampCardListResponse
import com.quinti.android_step_template.kmp.data.api.response.UncheckedCoinPrizeResponse
import com.quinti.android_step_template.kmp.data.api.response.base.Response
import io.ktor.client.request.parameter

interface KyashCoinApi {
    suspend fun getDailyRouletteStatus(): Response<DailyRouletteStatusResponse>

    suspend fun getCurrentKyashCoin(): Response<CurrentKyashCoinResponse>

    suspend fun getPrizes(
        type: String,
        status: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse>

    suspend fun getAppliedPrizes(
        type: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse>

    suspend fun getUncheckedPrizes(): Response<UncheckedCoinPrizeResponse>

    suspend fun getPrize(id: String): Response<CoinPrizeResponse>

    suspend fun applyCoinPrize(prizeId: String): Response<ApplyCoinPrizeResponse>

    suspend fun getDailyRouletteOptions(): Response<DailyRouletteOptionsResponse>

    suspend fun getDailyRouletteResult(): Response<DailyRouletteResultResponse>

    suspend fun sharedToSns(prizeId: String)

    suspend fun receivePrize(applicationId: String): Response<ReceivePrizeResponse>

    suspend fun checkLotteryResult(prizeId: String)

    suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<KyashCoinHistoryListResponse>

    suspend fun getStampCardList(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<StampCardListResponse>

    suspend fun exchangeStampCardReward(id: String): Response<ExchangeStampCardResponse>

    suspend fun getRewardOnboardingEvent(): Response<GetRewardOnboardingEventResponse>

    suspend fun postRewardOnboardingEvent()
}

class KyashCoinApiImpl(
    httpClient: SingletonProvider<KyashHttpClient>,
    endpoint: Provider<ApiEndpoint>,
    session: Provider<ApiSession>,
) : KyashApi(httpClient, endpoint, session), KyashCoinApi {
    override suspend fun getDailyRouletteStatus(): Response<DailyRouletteStatusResponse> {
        return getV2(apiUrl("/v1/coin/daily_roulette/status")) {}
    }

    override suspend fun getCurrentKyashCoin(): Response<CurrentKyashCoinResponse> {
        return getV2(apiUrl("/v1/coin/balance")) {}
    }

    override suspend fun getPrizes(
        type: String,
        status: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse> {
        return getV2(apiUrl("/v1/coin/prizes")) {
            page?.let {
                parameter("page", page)
            }
            parameter("limit", limit)
            parameter("type", type)
            parameter("status", status)
        }
    }

    override suspend fun getAppliedPrizes(
        type: String,
        page: Int?,
        limit: Int,
    ): Response<CoinPrizeListResponse> {
        return getV2(apiUrl("/v1/coin/prizes/applied")) {
            parameter("type", type)
            page?.let {
                parameter("page", page)
            }
            parameter("limit", limit)
        }
    }

    override suspend fun getUncheckedPrizes(): Response<UncheckedCoinPrizeResponse> {
        return getV2(apiUrl("/v1/coin/prizes/unchecked")) {}
    }

    override suspend fun getPrize(id: String): Response<CoinPrizeResponse> {
        return getV2(apiUrl("/v1/coin/prizes/$id")) {}
    }

    override suspend fun applyCoinPrize(prizeId: String): Response<ApplyCoinPrizeResponse> {
        return postV2(apiUrl("/v2/coin/prizes/$prizeId/apply")) {}
    }

    override suspend fun getDailyRouletteOptions(): Response<DailyRouletteOptionsResponse> {
        return getV2(apiUrl("/v1/coin/daily_roulette/options")) {}
    }

    override suspend fun getDailyRouletteResult(): Response<DailyRouletteResultResponse> {
        return postV2(apiUrl("/v1/coin/daily_roulette")) {}
    }

    override suspend fun sharedToSns(prizeId: String) {
        return postV2(apiUrl("/v1/coin/prizes/$prizeId/share_sns")) {}
    }

    override suspend fun receivePrize(applicationId: String): Response<ReceivePrizeResponse> {
        return postV2(apiUrl("/v1/coin/prizes/application/$applicationId/receive")) {
        }
    }

    override suspend fun checkLotteryResult(prizeId: String) {
        return putV2(apiUrl("/v1/coin/prizes/$prizeId/check")) {}
    }

    override suspend fun getKyashCoinHistory(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<KyashCoinHistoryListResponse> {
        return getV2(apiUrl("/v1/coin/histories")) {
            if (lastCreatedAt != null) {
                parameter("lastCreatedAt", lastCreatedAt)
            }
            parameter("limit", limit)
        }
    }

    override suspend fun getStampCardList(
        lastCreatedAt: String?,
        limit: Int,
    ): Response<StampCardListResponse> {
        return getV2(apiUrl("/v1/coin/stampcards")) {
            if (lastCreatedAt != null) {
                parameter("lastCreatedAt", lastCreatedAt)
            }
            parameter("limit", limit)
        }
    }

    override suspend fun exchangeStampCardReward(id: String): Response<ExchangeStampCardResponse> {
        return postV2(apiUrl("/v1/coin/stampcards/$id/exchange")) {}
    }

    override suspend fun getRewardOnboardingEvent(): Response<GetRewardOnboardingEventResponse> {
        return getV2(apiUrl("/v1/coin/user_events/onboarding")) {}
    }

    override suspend fun postRewardOnboardingEvent() {
        return postV2(apiUrl("/v1/coin/user_events/onboarding")) {}
    }
}