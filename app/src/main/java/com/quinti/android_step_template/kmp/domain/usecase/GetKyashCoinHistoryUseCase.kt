package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.entity.KyashCoinHistoryList
import com.quinti.android_step_template.kmp.data.repository.KyashCoinRepository

interface GetKyashCoinHistoryUseCase {
    suspend operator fun invoke(
        lastCreatedAt: String? = null,
    ): KyashCoinHistoryList
}

internal class GetKyashCoinHistory(
    val repository: KyashCoinRepository,
) : GetKyashCoinHistoryUseCase {
    override suspend fun invoke(
        lastCreatedAt: String?,
    ): KyashCoinHistoryList {
        return repository.getKyashCoinHistory(
            lastCreatedAt = lastCreatedAt,
        )
    }
}