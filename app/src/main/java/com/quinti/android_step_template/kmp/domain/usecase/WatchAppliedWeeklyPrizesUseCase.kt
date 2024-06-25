package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchAppliedWeeklyPrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchAppliedWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : WatchAppliedWeeklyPrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.appliedWeeklyPrizes
    }
}