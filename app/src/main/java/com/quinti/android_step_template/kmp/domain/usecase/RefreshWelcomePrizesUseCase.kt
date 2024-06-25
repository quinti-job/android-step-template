package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface RefreshWelcomePrizesUseCase {
    suspend operator fun invoke()
}

internal class RefreshWelcomePrizes(
    private val prizeRepository: PrizeRepository,
    private val isWelcomeChallengeEnabled: Boolean,
) : RefreshWelcomePrizesUseCase {
    override suspend fun invoke() {
        if (!isWelcomeChallengeEnabled) {
            return
        }
        prizeRepository.loadWelcomePrizes(
            refresh = true,
        )
    }
}