package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.model.reward.offerwall.SkyFlagOfferWallEngagementUri
import co.kyash.mobile.repository.OfferWallRepository

interface ExtractSkyFlagOfferWallPromotionUrlUseCase {
    /**
     * Convert the given URI to [SkyFlagOfferWallEngagementUri]
     *
     * @param [uri] The URI to convert.
     * @return The promotion URL.
     * If the URI is not a valid sky flag offer wall engagement URI.
     */
    suspend operator fun invoke(uri: SkyFlagOfferWallEngagementUri.Promotion): String
}

internal class ExtractSkyFlagOfferWallPromotionUrl(
    private val repository: OfferWallRepository,
) : ExtractSkyFlagOfferWallPromotionUrlUseCase {
    override suspend fun invoke(uri: SkyFlagOfferWallEngagementUri.Promotion): String {
        return repository.decodeSkyFlagPromotionUrl(uri)
    }
}