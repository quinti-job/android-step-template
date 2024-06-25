package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository


interface RefreshAppliedWeeklyPrizesUseCase {
    suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    )
}

internal class RefreshAppliedWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : RefreshAppliedWeeklyPrizesUseCase {
    override suspend fun invoke(
        filter: AppliedPrizeFilter,
    ) {
        prizeRepository.loadAppliedWeeklyPrizes(
            refresh = true,
            filter = filter,
        )
    }
}