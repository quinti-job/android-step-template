package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.KyashCoinRepository

interface SetShowRewardTopOnboardingUseCase {
    suspend operator fun invoke()
}

internal class SetShowRewardTopOnboarding(
    private val kyashCoinRepository: KyashCoinRepository,
) : SetShowRewardTopOnboardingUseCase {
    override suspend fun invoke() {
        kyashCoinRepository.setRewardTopOnboardingShown()
    }
}