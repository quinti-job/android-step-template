package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.DailyRouletteOptions
import co.kyash.mobile.repository.KyashCoinRepository

interface GetDailyRouletteOptionsUseCase {
    suspend operator fun invoke(): DailyRouletteOptions
}

internal class GetDailyRouletteOptions(
    private val repository: KyashCoinRepository,
) : GetDailyRouletteOptionsUseCase {
    override suspend fun invoke(): DailyRouletteOptions {
        return repository.getDailyRouletteOptions().let {
            it.copy(
                coinAmounts = it.coinAmounts.shuffled(),
            )
        }
    }
}