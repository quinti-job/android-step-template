package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.PrizeRepository
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface RefreshActiveWeeklyPrizesUseCase {
    suspend operator fun invoke()
}

internal class RefreshActiveWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : RefreshActiveWeeklyPrizesUseCase {
    override suspend fun invoke() {
        prizeRepository.loadActiveWeeklyPrizes(
            refresh = true,
        )
    }
}