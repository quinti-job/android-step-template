package com.quinti.android_step_template.kmp.domain.usecase

import co.kyash.mobile.repository.OfferWallRepository

interface AgreeToProvidePersonalInformationForSkyFlagUseCase {
    suspend operator fun invoke()
}

internal class AgreeToProvidePersonalInformationUseCaseForSkyFlag(
    private val repository: OfferWallRepository,
) : AgreeToProvidePersonalInformationForSkyFlagUseCase {
    override suspend fun invoke() {
        repository.agreeToProvidePersonalInformationForSkyFlag()
    }
}