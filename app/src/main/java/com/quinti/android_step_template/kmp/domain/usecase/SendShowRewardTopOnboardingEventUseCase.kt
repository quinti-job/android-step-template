package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.KyashCoinRepository
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

interface SendShowRewardTopOnboardingEventUseCase {
    suspend operator fun invoke()
}

internal class SendShowRewardTopOnboardingEvent(
    private val kyashCoinRepository: KyashCoinRepository,
) : SendShowRewardTopOnboardingEventUseCase {
    override suspend fun invoke() {
        kyashCoinRepository.sendRewardTopOnboardingShowEvent()
    }
}