package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.DailyRouletteStatus
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

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