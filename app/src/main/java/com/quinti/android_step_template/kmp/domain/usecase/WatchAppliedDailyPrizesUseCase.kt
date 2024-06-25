package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchAppliedDailyPrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchAppliedDailyPrizes(
    private val prizeRepository: PrizeRepository,
) : WatchAppliedDailyPrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.appliedDailyPrizes
    }
}