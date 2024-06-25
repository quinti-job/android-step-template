package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface RefreshAppliedDailyPrizesUseCase {
    suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    )
}

internal class RefreshAppliedDailyPrizes(
    private val prizeRepository: PrizeRepository,
) : RefreshAppliedDailyPrizesUseCase {
    override suspend fun invoke(
        filter: AppliedPrizeFilter,
    ) {
        prizeRepository.loadAppliedDailyPrizes(
            refresh = true,
            filter = filter,
        )
    }
}