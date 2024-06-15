package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.KyashCoinRepository

interface NeedShowRewardTopOnboardingUseCase {
    suspend operator fun invoke(): Boolean
}

internal class NeedShowRewardTopOnboarding(
    private val kyashCoinRepository: KyashCoinRepository,
) : NeedShowRewardTopOnboardingUseCase {
    override suspend fun invoke(): Boolean {
        val isShown = kyashCoinRepository.getRewardTopOnboardingShown()
        return !isShown
    }
}