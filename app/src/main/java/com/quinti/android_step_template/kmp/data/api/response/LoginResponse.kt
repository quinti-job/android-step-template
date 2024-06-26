package com.quinti.android_step_template.kmp.data.api.response

import com.quinti.android_step_template.kmp.data.entity.Login
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val status: Boolean,
    val token: String,
    val user_id: String,
) {
    fun toEntity(): Login {
        return Login(
            status = status,
            token = token,
            userId = user_id,
        )
    }
}