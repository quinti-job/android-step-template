package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface QueryAppliedWeeklyPrizesUseCase {
    suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    )
}

internal class QueryAppliedWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : QueryAppliedWeeklyPrizesUseCase {
    override suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    ) {
        prizeRepository.loadAppliedWeeklyPrizes(
            refresh = false,
            filter = filter,
        )
    }
}