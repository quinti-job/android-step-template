package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.AppliedPrizeFilter
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface QueryAppliedDailyPrizesUseCase {
    suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    )
}

internal class QueryAppliedDailyPrizes(
    private val prizeRepository: PrizeRepository,
) : QueryAppliedDailyPrizesUseCase {
    override suspend operator fun invoke(
        filter: AppliedPrizeFilter,
    ) {
        prizeRepository.loadAppliedDailyPrizes(
            refresh = false,
            filter = filter,
        )
    }
}