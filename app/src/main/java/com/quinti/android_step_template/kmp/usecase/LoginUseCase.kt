package com.quinti.android_step_template.kmp.usecase

import com.quinti.android_step_template.kmp.data.api.entity.Login
import com.quinti.android_step_template.kmp.data.repository.LoginRepository


interface LoginUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Login
}

internal class PostLogin(
    private val repository: LoginRepository,
) : LoginUseCase {
    override suspend fun invoke(
        email: String,
        password: String,
    ): Login {
        return repository.postLogin(
            email = email,
            password = password,
        )
    }
}