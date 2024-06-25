package com.quinti.android_step_template.kmp.domain.usecase

import com.quinti.android_step_template.kmp.data.repository.PointRepository
import com.quinti.android_step_template.ui.component.PointBalance

interface GetPointBalanceUseCase {
    suspend operator fun invoke(): Result<PointBalance>
}

internal class GetPointBalance(
    private val repository: PointRepository,
) : GetPointBalanceUseCase {
    override suspend fun invoke(): Result<PointBalance> {
        return repository.getPointBalance()
    }
}