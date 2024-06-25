package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.PrizeRepository

interface GetUncheckRewardCountUseCase {
    suspend operator fun invoke(): Int
}

internal class GetUncheckRewardCount(
    val repository: PrizeRepository,
) : GetUncheckRewardCountUseCase {
    override suspend fun invoke(): Int {
        // uncheckedの懸賞を取得する
        return repository.getUncheckedPrizes()?.count ?: 0
    }
}