package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.StampCard
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

interface GetCurrentStampCardUseCase {
    suspend operator fun invoke(): StampCard?
}

internal class GetCurrentStampCard(
    private val repository: KyashCoinRepository,
) : GetCurrentStampCardUseCase {
    override suspend fun invoke(): StampCard? {
        return repository.getCurrentStampCard()
    }
}