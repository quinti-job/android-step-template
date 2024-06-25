package com.quinti.android_step_template.kmp.data.api.response

import kotlinx.serialization.Serializable

@Serializable
data class GetRewardOnboardingEventResponse(
    val isOnboardingStarted: Boolean,
)