package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.KyashCoinRepository

interface RequestUpdateRewardTabIndicatorUseCase {
    suspend operator fun invoke()
}

internal class RequestUpdateRewardTabIndicator(
    val kyashCoinRepository: KyashCoinRepository,
) : RequestUpdateRewardTabIndicatorUseCase {
    override suspend fun invoke() {
        kyashCoinRepository.requestUpdateRewardTabIndicator()
    }
}