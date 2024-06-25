package com.quinti.android_step_template.kmp.domain.usecase.account

import com.quinti.android_step_template.kmp.data.entity.Login
import com.quinti.android_step_template.kmp.data.repository.AccountRepository


interface LoginUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): Login
}

internal class LoginUseCaseImpl(
    private val repository: AccountRepository,
) : LoginUseCase {
    override suspend fun invoke(
        email: String,
        password: String,
    ): Login {
        return repository.login(
            email = email,
            password = password,
        )
    }
}