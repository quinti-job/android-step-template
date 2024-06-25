package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

interface NeedShowRewardStampOnboardingUseCase {
    suspend operator fun invoke(): Boolean
}

internal class NeedShowRewardStampOnboarding(
    private val kyashCoinRepository: KyashCoinRepository,
) : NeedShowRewardStampOnboardingUseCase {
    override suspend fun invoke(): Boolean {
        return kyashCoinRepository.isNeedShowRewardStampOnboarding()
    }
}