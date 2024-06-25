package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

interface NeedShowRewardSpotlightUseCase {
    suspend operator fun invoke(): Boolean
}

internal class NeedShowRewardSpotlight(
    private val kyashCoinRepository: KyashCoinRepository,
) : NeedShowRewardSpotlightUseCase {
    override suspend fun invoke(): Boolean {
        return kyashCoinRepository.getRewardOnboardingShown().not()
    }
}