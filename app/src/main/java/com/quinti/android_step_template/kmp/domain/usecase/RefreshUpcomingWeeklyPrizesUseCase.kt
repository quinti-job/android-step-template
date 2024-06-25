package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface RefreshUpcomingWeeklyPrizesUseCase {
    suspend operator fun invoke()
}

internal class RefreshUpcomingWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : RefreshUpcomingWeeklyPrizesUseCase {
    override suspend fun invoke() {
        prizeRepository.loadUpcomingWeeklyPrizes(
            refresh = true,
        )
    }
}