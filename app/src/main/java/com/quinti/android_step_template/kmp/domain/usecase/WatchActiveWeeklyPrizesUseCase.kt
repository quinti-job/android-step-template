package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchActiveWeeklyPrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchActiveWeeklyPrizes(
    private val prizeRepository: PrizeRepository,
) : WatchActiveWeeklyPrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.activeWeeklyPrizes
    }
}