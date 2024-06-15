package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.PrizeList
import co.kyash.mobile.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchActiveDailyPrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchActiveDailyPrizes(
    private val prizeRepository: PrizeRepository,
) : WatchActiveDailyPrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.activeDailyPrizes
    }
}