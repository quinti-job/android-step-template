package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.offerwall.SkyFlagOfferWall
import co.kyash.mobile.repository.OfferWallRepository

interface GetSkyFlagOfferWallUseCase {
    suspend operator fun invoke(): SkyFlagOfferWall
}

internal class GetSkyFlagOfferWall(
    private val repository: OfferWallRepository,
) : GetSkyFlagOfferWallUseCase {
    override suspend fun invoke(): SkyFlagOfferWall {
        return repository.getSkyFlagOfferWall()
    }
}