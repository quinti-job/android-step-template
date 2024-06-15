package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.DailyRouletteStatus
import co.kyash.mobile.repository.KyashCoinRepository

interface GetDailyRouletteStatusUseCase {
    suspend operator fun invoke(): DailyRouletteStatus
}

internal class GetDailyRouletteStatus(
    val repository: KyashCoinRepository,
) : GetDailyRouletteStatusUseCase {
    override suspend fun invoke(): DailyRouletteStatus {
        return repository.getDailyRouletteStatus()
    }
}