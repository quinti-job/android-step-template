package com.quinti.android_step_template.kmp.data.repository

import com.quinti.android_step_template.kmp.data.api.PointApi
import com.quinti.android_step_template.ui.component.PointBalance

interface PointRepository {
    suspend fun getPointBalance(): Result<PointBalance>
}

class PointRepositoryImpl(
    private val api: PointApi,
) : PointRepository {

    override suspend fun getPointBalance(): Result<PointBalance> {
        return api.getPointBalance().map { response ->
            response.result.data.toDomainModel()
        }
    }
}