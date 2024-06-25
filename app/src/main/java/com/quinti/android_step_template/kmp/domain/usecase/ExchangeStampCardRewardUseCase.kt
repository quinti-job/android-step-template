package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.StampCardExchangeResult
import co.kyash.mobile.repository.KyashCoinRepository
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository
import com.quinti.android_step_template.kmp.data.repository.StampCardExchangeResult

interface ExchangeStampCardRewardUseCase {
    suspend operator fun invoke(id: String): StampCardExchangeResult
}

internal class ExchangeStampCardReward(
    private val repository: KyashCoinRepository,
) : ExchangeStampCardRewardUseCase {
    override suspend fun invoke(id: String): StampCardExchangeResult {
        return repository.exchangeStampReward(id)
    }
}