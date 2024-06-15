package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.KyashCoinRepository

interface SetShowRewardStampOnboardingUseCase {
    suspend operator fun invoke(needShow: Boolean)
}

internal class SetShowRewardStampOnboarding(
    private val kyashCoinRepository: KyashCoinRepository,
) : SetShowRewardStampOnboardingUseCase {
    override suspend fun invoke(needShow: Boolean) {
        kyashCoinRepository.setNeedShowRewardStampOnboarding(needShow)
    }
}