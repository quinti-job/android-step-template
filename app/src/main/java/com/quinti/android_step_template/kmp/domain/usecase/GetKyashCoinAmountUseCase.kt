package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.KyashCoin
import co.kyash.mobile.repository.KyashCoinRepository

interface GetKyashCoinAmountUseCase {
    suspend operator fun invoke(): KyashCoin
}

internal class GetKyashCoinAmount(
    val repository: KyashCoinRepository,
) : GetKyashCoinAmountUseCase {
    override suspend fun invoke(): KyashCoin {
        return repository.getCurrentCoinAmount()
    }
}