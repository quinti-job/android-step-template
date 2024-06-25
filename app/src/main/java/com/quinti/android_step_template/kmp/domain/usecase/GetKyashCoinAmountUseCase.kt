package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.KyashCoin
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

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