package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.PrizeList
import com.quinti.android_step_template.kmp.data.repository.PrizeRepository
import kotlinx.coroutines.flow.Flow

interface WatchWelcomePrizesUseCase {
    operator fun invoke(): Flow<PrizeList>
}

internal class WatchWelcomePrizes(
    private val prizeRepository: PrizeRepository,
) : WatchWelcomePrizesUseCase {
    override fun invoke(): Flow<PrizeList> {
        return prizeRepository.welcomePrizes
    }
}