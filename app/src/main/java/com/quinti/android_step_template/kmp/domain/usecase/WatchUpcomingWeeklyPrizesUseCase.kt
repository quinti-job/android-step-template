package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchUpcomingWeeklyPrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchUpcomingWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : WatchUpcomingWeeklyPrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.upcomingWeeklyPrizes
    }
}