package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.PrizeRepository
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface RefreshActiveDailyPrizesUseCase {
    suspend operator fun invoke()
}

internal class RefreshActiveDailyPrizes(
    private val prizeRepository: PrizeRepository,
) : RefreshActiveDailyPrizesUseCase {
    override suspend fun invoke() {
        prizeRepository.loadActiveDailyPrizes(
            refresh = true,
        )
    }
}